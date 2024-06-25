package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import RecyclerView.EventsAdapter;
import Session.Events;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Previous_Meetings extends AppCompatActivity {

    private static final String TAG = "Previous_Meetings";

    RecyclerView recyclerView;
    private EventsAdapter eventAdapter;

    private String accessToken;
    private List<Events> previousEventList;
    private OkHttpClient client;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_meetings);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        previousEventList = new ArrayList<>();
        eventAdapter = new EventsAdapter(previousEventList,this);
        recyclerView.setAdapter(eventAdapter);

        client = new OkHttpClient();
        gson = new Gson();

        fetchUpcomingEvents();
    }

    private void fetchUpcomingEvents() {
        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/user/pastgoing-events")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("accept", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("API Error", "Failed to fetch events", e);
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.isSuccessful()) {
                        String responseBody = response.body().string();
                        Log.d(TAG, "Response Body: " + responseBody);

                        if (responseBody != null) {
                            List<Events> events = gson.fromJson(responseBody, new TypeToken<List<Events>>() {
                            }.getType());

                            if (events != null) {
                                runOnUiThread(() -> {
                                    previousEventList.clear();
                                    previousEventList.addAll(events);
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
