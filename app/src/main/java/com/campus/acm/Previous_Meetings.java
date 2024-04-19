package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import RecyclerView.EventsAdapter;
import Session.Events;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Previous_Meetings extends AppCompatActivity {

    RecyclerView recyclerView;
    private EventsAdapter eventAdapter;
    private List<Events> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_previous_meetings);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        eventList = new ArrayList<>();
        eventAdapter = new EventsAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String access_token = sharedPreferences.getString("access_token", "");
        //String accessToken = sharedPreferences.getString("access_token", "");
        fetchPreviousMeetings(access_token);
    }
    // fetchPreviousMeetings();




    private void fetchPreviousMeetings(String access_token) {
        Log.d("AccessToken", "access_token: " + access_token);
        OkHttpClient client = new OkHttpClient();
        String url = "http://90.84.199.65:8000/user/pastgoing-events";
        // String authorizationHeader = "Bearer " + access_token ;
        // Log.d("NetworkRequest", "Authorization Header: " + authorizationHeader);

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + access_token)
                .addHeader("Content-Type", "application/json")
                .build();

        Log.d("NetworkRequest", "Request Headers: " + request.headers());
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Previous_Meetings.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {

                    if (response.code() == 204) {
                        runOnUiThread(() -> Toast.makeText(Previous_Meetings.this, "Request processed successfully, but no new data is available.", Toast.LENGTH_SHORT).show());

                    } else {
                        String responseBody = response.body().string();
                        if (responseBody != null && !responseBody.isEmpty()) {
                            runOnUiThread(() -> {
                                Toast.makeText(Previous_Meetings.this, "Data is fetched.", Toast.LENGTH_SHORT).show();
                                List<Events> pastEvents = parseJsonResponse(responseBody);
                                eventList.clear();
                                eventList.addAll(pastEvents);
                                eventAdapter.notifyDataSetChanged();
                            });
                        } else {
                            runOnUiThread(() -> Toast.makeText(Previous_Meetings.this, "No data available.", Toast.LENGTH_SHORT).show());
                        }
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(Previous_Meetings.this, "Failed to fetch previous meetings. Response code: " + response.code(), Toast.LENGTH_SHORT).show());
                    Log.d("Previous meetings", "Response body: " + response);

                }
            }
        });
    }

    private List<Events> parseJsonResponse(String responseBody) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Events>>() {
        }.getType();
        List<Events> allEvents = gson.fromJson(responseBody, listType);

        // Filter out future events

        List<Events> pastEvents = new ArrayList<>();
        Date currentDate = new Date();

        for (Events event : allEvents) {
            Date eventDate = event.getDate();
            if (eventDate.before(currentDate)) {
                pastEvents.add(event);
            }
        }

        return pastEvents;
    }
}



