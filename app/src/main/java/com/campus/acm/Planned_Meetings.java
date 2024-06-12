package com.campus.acm;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.TimeUnit;

import Session.Events;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Planned_Meetings extends AppCompatActivity {

    private LinearLayout meetingsContainer;
    private static final String TAG = "Planned_Meetings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_meetings);

        meetingsContainer = findViewById(R.id.meetings_container);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("access_token", "");

        fetchAllEvents(accessToken);
    }

    private void fetchAllEvents(String accessToken) {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build();

        String url = "http://90.84.199.65:8000/user/events";
        Log.d(TAG, "Request URL: " + url);
        Log.d(TAG, "Access Token: " + accessToken);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Error fetching events", e);
                runOnUiThread(() -> Toast.makeText(Planned_Meetings.this, "Error fetching events: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "Response Code: " + response.code());
                Log.d(TAG, "Response Headers: " + response.headers());

                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d(TAG, "Response Body: " + responseBody);

                    if (responseBody != null && !responseBody.isEmpty()) {
                        runOnUiThread(() -> {
                            Toast.makeText(Planned_Meetings.this, "Meetings fetched successfully.", Toast.LENGTH_SHORT).show();
                            List<Events> allEvents = parseJsonResponse(responseBody);
                            if (allEvents != null && !allEvents.isEmpty()) {
                                displayEventsInView(allEvents);
                            } else {
                                Toast.makeText(Planned_Meetings.this, "No events found", Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        runOnUiThread(() -> {
                            Toast.makeText(Planned_Meetings.this, "No data received from server", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Empty response body");
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(Planned_Meetings.this, "Failed to fetch events. Response code: " + response.code(), Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Failed to fetch events. Response code: " + response.code());
                    });
                }
            }
        });
    }

    private List<Events> parseJsonResponse(String responseBody) {
        try {
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Events>>() {}.getType();
            List<Events> events = gson.fromJson(responseBody, listType);
            Log.d(TAG, "Parsed events: " + events);
            return events;
        } catch (Exception e) {
            Log.e(TAG, "Error parsing JSON", e);
            return null;
        }
    }

    private void displayEventsInView(List<Events> allEvents) {
        if (allEvents != null) {
            meetingsContainer.removeAllViews();
            for (Events event : allEvents) {
                TextView textView = new TextView(this);
                textView.setText("Event: " + event.getEvent_name() + ", Date: " + event.getDate());
                textView.setTextSize(18);
                textView.setTextColor(Color.BLACK);
                textView.setPadding(16, 16, 16, 16);
                meetingsContainer.addView(textView);

                Log.d(TAG, "Event: " + event.getEvent_name() + ", Date: " + event.getDate());
            }
        } else {
            Toast.makeText(this, "No events found", Toast.LENGTH_SHORT).show();
        }
    }
}
