package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
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

public class Planned_Meetings extends AppCompatActivity {

    RecyclerView recyclerView;
    private EventsAdapter eventAdapter;
    private List<Events> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_planned_meetings);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        eventList = new ArrayList<>();
        eventAdapter = new EventsAdapter(eventList);
        recyclerView.setAdapter(eventAdapter);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String access_token = sharedPreferences.getString("access_token", "");

        fetchAllEvents(access_token);
    }

    private void fetchAllEvents(String access_token) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://90.84.199.65:8000/user/events";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + access_token)
                .addHeader("Content-Type", "application/json")
                .addHeader("Accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Planned_Meetings.this, "Error fetching events: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    runOnUiThread(() -> {
                        Toast.makeText(Planned_Meetings.this, "Events fetched successfully.", Toast.LENGTH_SHORT).show();
                        List<Events> allEvents = parseJsonResponse(responseBody);
                        displayEventsInRecyclerView(allEvents);
                    });
                } else {
                    runOnUiThread(() -> Toast.makeText(Planned_Meetings.this, "Failed to fetch events. Response code: " + response.code(), Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    private List<Events> parseJsonResponse(String responseBody) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Events>>() {}.getType();
        return gson.fromJson(responseBody, listType);
    }

    private void displayEventsInRecyclerView(List<Events> allEvents) {
        if (allEvents != null) {
            eventList.clear();
            eventList.addAll(allEvents);
            eventAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "No events found", Toast.LENGTH_SHORT).show();
        }
    }
}
