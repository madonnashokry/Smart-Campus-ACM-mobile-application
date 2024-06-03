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
import java.util.List;

import RecyclerView.EventsAdapter;
import Session.Events;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class upcoming_meetings extends AppCompatActivity {
    private RecyclerView recyclerView;
    private EventsAdapter eventAdapter;
    private List<Events> upcomingEventList;
    private OkHttpClient client;
    private Gson gson;
    private String accessToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_meetings);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        upcomingEventList = new ArrayList<>();
        eventAdapter = new EventsAdapter(upcomingEventList);
        recyclerView.setAdapter(eventAdapter);

        client = new OkHttpClient();
        gson = new Gson();

        fetchUpcomingEvents();
    }

    private void fetchUpcomingEvents() {

        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/user/upcoming-events")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(upcoming_meetings.this, "Failed to fetch upcoming events: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("upcoming_meetings", "onFailure: ", e);
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("upcoming_meetings", "Response: " + responseBody);

                    if (responseBody.isEmpty()) {
                        runOnUiThread(() -> {
                            Toast.makeText(upcoming_meetings.this, "No upcoming events found.", Toast.LENGTH_SHORT).show();
                            updateEvents(new ArrayList<>()); // Clear the list if response is empty
                        });
                        return;
                    }

                    try {
                        Type eventType = new TypeToken<List<Events>>(){}.getType();
                        List<Events> events = gson.fromJson(responseBody, eventType);

                        runOnUiThread(() -> updateEvents(events));
                    } catch (Exception e) {
                        Log.e("upcoming_meetings", "Failed to parse response: ", e);
                        runOnUiThread(() -> {
                            Toast.makeText(upcoming_meetings.this, "Failed to parse events", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    runOnUiThread(() -> {
                        Toast.makeText(upcoming_meetings.this, "Failed to fetch upcoming events: " + response.message(), Toast.LENGTH_SHORT).show();
                        Log.e("upcoming_meetings", "Failed to fetch upcoming events: " + response.message());
                    });
                }
            }
        });
    }

    private void updateEvents(List<Events> events) {
        if (events == null) {
            Log.e("upcoming_meetings", "updateEvents: received null events list");
            return;
        }
        upcomingEventList.clear();
        upcomingEventList.addAll(events);
        eventAdapter.notifyDataSetChanged();
    }
}
