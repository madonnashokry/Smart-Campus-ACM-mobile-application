package com.campus.acm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import RecyclerView.EventsAdapter;
import Session.Events;


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
       // fetchPreviousMeetings();
    }
   // fetchPreviousMeetings();

    /*
    private void fetchPreviousMeetings() {
        OkHttpClient client = new OkHttpClient();

        String url = "http://90.84.199.65:8000/user/pastgoing-events";

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Previous_Meetings.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    List<Events> events = parseJsonResponse(responseBody);
                    runOnUiThread(() -> Toast.makeText(Previous_Meetings.this, " successfull", Toast.LENGTH_SHORT).show());
                    runOnUiThread(() -> {
                        eventList.clear();
                        eventList.addAll(events);
                        eventAdapter.notifyDataSetChanged();
                    });
                } else {
                    if (!response.isSuccessful()) {
                        // Handle unsuccessful response
                        runOnUiThread(() -> Toast.makeText(Previous_Meetings.this, " failed", Toast.LENGTH_SHORT).show());
                        return;
                    }

                }
            }
        });
    }

    private List<Events> parseJsonResponse(String responseBody) {
        Gson gson = new Gson();
        Type listType = new TypeToken<List<Events>>(){}.getType();
        return gson.fromJson(responseBody, listType);
    }

     */

}

