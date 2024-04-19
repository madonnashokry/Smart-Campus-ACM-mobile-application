package com.campus.acm;

import android.os.Bundle;
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

public class upcoming_meetings extends AppCompatActivity {
    RecyclerView recyclerView;
    private EventsAdapter eventAdapter;
    private List<Events> UPcoming_eventList;
    private List<Events> eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_upcoming_meetings);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        UPcoming_eventList = new ArrayList<>();
        eventAdapter = new EventsAdapter(UPcoming_eventList);
        recyclerView.setAdapter(eventAdapter);
    }



    private void up_comingMeetings(String accessToken) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://90.84.199.65:8000/user/upcoming-events";

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(upcoming_meetings.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }


            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    if (response.code() == 204) {
                        runOnUiThread(() -> Toast.makeText(upcoming_meetings.this, "Request processed successfully, but no new data is available.", Toast.LENGTH_SHORT).show());
                    } else {
                        String responseBody = response.body().string();
                        if (responseBody != null && !responseBody.isEmpty()) {
                            List<Events> futureEvents = parseJsonResponse(responseBody);
                            runOnUiThread(() -> {
                                eventList.clear();
                                eventList.addAll(futureEvents);
                                eventAdapter.notifyDataSetChanged();
                            });
                        } else {
                            runOnUiThread(() -> Toast.makeText(upcoming_meetings.this, "No data available.", Toast.LENGTH_SHORT).show());
                        }
                    }
                } else {
                    runOnUiThread(() -> Toast.makeText(upcoming_meetings.this, "Failed to fetch previous meetings. Response code: " + response.code(), Toast.LENGTH_SHORT).show());
                }
            }

        });
    }

    private List<Events> parseJsonResponse(String responseBody) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Events>>(){}.getType();
        List<Events> allEvents = gson.fromJson(responseBody, listType);

        // Filter out future events

        List<Events> pastEvents = new ArrayList<>();
        Date currentDate = new Date();

        for (Events event : allEvents) {
            Date eventDate = event.getDate(); // Assuming getDate() returns the event date as a Date
            if (eventDate.after(currentDate)) {
                pastEvents.add(event);
            }
        }

        return pastEvents;
    }

}
