package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import Session.LogItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class event_loggg extends AppCompatActivity {
    String accessToken;
    LogItemAdapter logItemAdapter;
    OkHttpClient client;
    RecyclerView logitemfile;

    List<LogItem> logItemList = new ArrayList<>(); //  list of log items

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event_loggg);
        logitemfile = findViewById(R.id.lotrecyc);
        logitemfile.setLayoutManager(new LinearLayoutManager(this));
        logItemAdapter = new LogItemAdapter(logItemList);
        logitemfile.setAdapter(logItemAdapter); // Set the adapter only once


        client = new OkHttpClient();
        logitemfile.setLayoutManager(new LinearLayoutManager(this));
        logItemAdapter = new LogItemAdapter(logItemList);
        logitemfile.setAdapter(logItemAdapter); // Set the adapter only once

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        client = new OkHttpClient();

        // Get the event ID from the intent extras
        int eventID = getIntent().getIntExtra("event_id", -1);
        if (eventID != -1) {
            fetchLogItems(eventID); // Fetch log items directly
        } else {
            Toast.makeText(this, "Event ID not provided", Toast.LENGTH_SHORT).show();
        }


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
                Log.e("Logfile", " failed: " + e.getMessage());
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
                            Toast.makeText(event_loggg.this, "Fetched successfully but no meetings attended", Toast.LENGTH_SHORT).show();
                            logitemfile.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(event_loggg.this, "Fetched successfully", Toast.LENGTH_SHORT).show();
                            logitemfile.setVisibility(View.VISIBLE);
                            logItemList.addAll(fetchedLogItems); // Add new items to the list
                            logItemAdapter.notifyDataSetChanged(); // Notify the adapter of data changes
                        }
                    });
                } else {
                    Toast.makeText(event_loggg.this, "Fetched unsuccessfully", Toast.LENGTH_SHORT).show();
                    Log.e("Logfile", "API call unsuccessful: " + response.message());
                }
            }
 });




    }
}