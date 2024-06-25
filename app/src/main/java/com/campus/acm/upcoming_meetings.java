package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
        eventAdapter = new EventsAdapter(upcomingEventList,this
        );
        recyclerView.setAdapter(eventAdapter);

        client = new OkHttpClient();
        gson = new Gson();

        fetchUpcomingEvents();
    }

    private void fetchUpcomingEvents() {
        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/user/upcoming-events")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("API Error", "Failed to fetch events", e);
            }


            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String jsonData = response.body().string();
                        if (jsonData != null) { // Check if jsonData is not null
                            Events[] eventsArray = gson.fromJson(jsonData, Events[].class);

                            if (eventsArray != null) { // Check if eventsArray is not null
                                runOnUiThread(() -> {
                                    upcomingEventList.clear();
                                    upcomingEventList.addAll(Arrays.asList(eventsArray));
                                    eventAdapter.notifyDataSetChanged();
                                });
                            } else {
                                Log.e("API Error", "Failed to parse events array");
                            }
                        } else {
                            Log.e("API Error", "Response body is null");
                        }
                    } else {
                        Log.e("API Error", "Failed to fetch events with status code " + response.code());
                    }
                } finally {
                    response.close();
                }
            }
        });
    }
}

