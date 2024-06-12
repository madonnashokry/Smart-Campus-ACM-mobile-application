package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
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
import java.util.List;

import Session.LogItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import RecyclerView.LogItemAdapter;
public class logfilee extends AppCompatActivity {
    ImageButton backbtn;
    String accessToken;

    LogItemAdapter logItemAdapter;
    OkHttpClient client;
    RecyclerView logitemfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_logfile);
        backbtn = findViewById(R.id.back);
        logitemfile = findViewById(R.id.lotrecyc);

        logitemfile.setLayoutManager(new LinearLayoutManager(this));


        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        client = new OkHttpClient();
        fetchLogItems();
    }

    private void fetchLogItems() {
        String url = "http://90.84.199.65:8000/event/log";

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
                    List<LogItem> logItemList = gson.fromJson(responseBody, listType);

                    // Update the RecyclerView on the main thread
                    new Handler(Looper.getMainLooper()).post(() -> {
                        if (logItemList == null || logItemList.isEmpty()) {
                            Toast.makeText(logfilee.this, "No meetings attended", Toast.LENGTH_SHORT).show();
                            logitemfile.setVisibility(View.GONE);
                        } else {
                            logitemfile.setVisibility(View.VISIBLE);
                            logItemAdapter = new LogItemAdapter(logItemList);
                            logitemfile.setAdapter(logItemAdapter);
                        }
                    });
                } else {
                    Log.e("Logfile", "API call unsuccessful: " + response.message());
                }
            }
        });
    }
}
