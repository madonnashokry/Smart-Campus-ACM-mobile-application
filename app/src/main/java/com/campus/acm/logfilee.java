package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import RecyclerView.LogItemAdapter;
import Session.LogItem;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class logfilee extends AppCompatActivity {
    ImageButton backbtn;
    String accessToken;
    LogItemAdapter logItemAdapter;
    OkHttpClient client;
    RecyclerView logitemfile;
    EditText eventIdInput;
    Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logfile);
        backbtn = findViewById(R.id.back);
        logitemfile = findViewById(R.id.lotrecyc);
        eventIdInput = findViewById(R.id.eventIdInput);
        submitBtn = findViewById(R.id.submitBtn);

        logitemfile.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        client = new OkHttpClient();

        submitBtn.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(eventIdInput.getText().toString())) {
                try {
                    int eventID = Integer.parseInt(eventIdInput.getText().toString());
                    fetchLogItems(eventID); // Fetch log items after getting the event ID
                } catch (NumberFormatException e) {
                    Toast.makeText(logfilee.this, "Invalid event ID", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(logfilee.this, "Event ID cannot be empty", Toast.LENGTH_SHORT).show();
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
                Log.e("Logfile", " failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Gson gson = new Gson();
                    Type listType = new TypeToken<List<LogItem>>() {}.getType();
                    final List<LogItem> logItemList = gson.fromJson(responseBody, listType);

                    runOnUiThread(() -> {
                        if (logItemList == null || logItemList.isEmpty()) {
                            Toast.makeText(logfilee.this, "fetched successes but No meetings attended", Toast.LENGTH_SHORT).show();
                            logitemfile.setVisibility(View.GONE);
                        } else {
                            Toast.makeText(logfilee.this, "fetched successes", Toast.LENGTH_SHORT).show();
                            logitemfile.setVisibility(View.VISIBLE);
                            logItemAdapter = new LogItemAdapter(logItemList);
                            logitemfile.setAdapter(logItemAdapter);
                        }
                    });
                } else {
                    Toast.makeText(logfilee.this, "fetched unsucess", Toast.LENGTH_SHORT).show();
                    Log.e("Logfile", "API call unsuccessful: " + response.message());
                }
            }
        });
    }
}
