package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
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

import RecyclerView.LogItemAdapter;
import Session.Events;
import Session.LogItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class logfilee extends AppCompatActivity {
    private static final String TAG = "LogfileActivity";

    ImageButton backbtn;
    String accessToken;
    LogItemAdapter logItemAdapter;
    OkHttpClient client;
    RecyclerView logitemfile;
    List<LogItem> logItemList = new ArrayList<>(); // list of log items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logfile);

        // Initialize views
        backbtn = findViewById(R.id.back);
        logitemfile = findViewById(R.id.lotrecyc);

        logitemfile.setLayoutManager(new LinearLayoutManager(this));
        logItemAdapter = new LogItemAdapter(logItemList);
        logitemfile.setAdapter(logItemAdapter); // Set the adapter only once

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        client = new OkHttpClient();

        fetchUserEvents();
    }

    private void fetchUserEvents() {
        String url = "http://90.84.199.65:8000/user/events";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Fetch user events failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<Events>>() {}.getType();
                    final List<Events> events = gson.fromJson(responseBody, listType);

                    runOnUiThread(() -> {
                        if (events == null || events.isEmpty()) {
                            Toast.makeText(logfilee.this, "No events found", Toast.LENGTH_SHORT).show();
                        } else {
                            for (Events event : events) {
                                fetchLogItems(event.getEvent_id());
                            }
                        }
                    });
                } else {
                    Toast.makeText(logfilee.this, "Fetch user events unsuccessful", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "API call unsuccessful: " + response.message());
                }
            }
        });
    }

    private void fetchLogItems(int eventID) {
        String url = "http://90.84.199.65:8000/event/" + eventID + "/log";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.e(TAG, "Fetch log items failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<LogItem>>() {}.getType();
                    final List<LogItem> fetchedLogItems = gson.fromJson(responseBody, listType);

                    runOnUiThread(() -> {
                        if (fetchedLogItems == null || fetchedLogItems.isEmpty()) {
                            Toast.makeText(logfilee.this, "Fetched successfully but no log items found", Toast.LENGTH_SHORT).show();
                            logitemfile.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(logfilee.this, "Fetched successfully", Toast.LENGTH_SHORT).show();
                            logitemfile.setVisibility(View.VISIBLE);
                            logItemList.addAll(fetchedLogItems); // Add new items to the list
                            logItemAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
                        }
                    });
                } else {
                    Toast.makeText(logfilee.this, "Fetch log items unsuccessful", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "API call unsuccessful: " + response.message());
                }
            }
        });
    }
}
