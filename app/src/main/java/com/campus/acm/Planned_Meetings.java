package com.campus.acm;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import RecyclerView.EventsAdapter;
import Session.Events;
import Session.user;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Planned_Meetings extends AppCompatActivity {

    ImageButton cale;
    private RecyclerView recyclerView;
    private EventsAdapter eventAdapter;
    private List<Events> EventsList;
    private OkHttpClient client;
    private Gson gson;
    private String accessToken;
    private String email;
    ImageButton editt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_planned_meetings);


        cale = findViewById(R.id.calndr_icon);
        //editt.setOnClickListener(v -> navigateToSchedulingActivity());
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        EventsList = new ArrayList<>();
        eventAdapter = new EventsAdapter(EventsList, this
        );
        //eventAdapter.setOnItemClickListener(this::navigateToSchedulingActivity);
        eventAdapter.setPlannedMeetingsActivity(true); // Set this to true if in PlannedMeetingsActivity

        recyclerView.setAdapter(eventAdapter);

        client = new OkHttpClient();
        gson = new Gson();

        fetchUserDetails();

        cale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CalendarButton", "Calendar button clicked");

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        Planned_Meetings.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        (view1, year1, month1, dayOfMonth) -> {
                            month1 = month1 + 1;
                            String date = year1 + "-" + String.format("%02d", month1) + "-" + String.format("%02d", dayOfMonth);
                            fetchEvents(date);
                        },
                        year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
    }

    private void navigateToSchedulingActivity(Events event) {
        Intent intent = new Intent(this, Scheduling.class);
        Log.d("NavigateToScheduling", "Navigating to scheduling activity with event ID: " + event.getEvent_id());

        startActivity(intent);
    }


    private void fetchUserDetails() {
        String url = "http://90.84.199.65:8000/user/info";
        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("API Error", "Failed to fetch user details", e);
                runOnUiThread(() -> {
                    Toast.makeText(Planned_Meetings.this, "Failed to load user details", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("API Response", "User details response: " + responseBody);
                    try {
                        user userDetails = gson.fromJson(responseBody, user.class);
                        if (userDetails != null && userDetails.getEmail() != null) {
                            email = userDetails.getEmail();
                            Log.d("User Email", "Fetched user email: " + email);
                            fetchEvents(); // Fetch all events initially
                        } else {
                            Log.e("API Error", "Failed to parse user details: userDetails is null or email is null");
                            runOnUiThread(() -> {
                                Toast.makeText(Planned_Meetings.this, "Failed to load user details", Toast.LENGTH_SHORT).show();
                            });
                        }
                    } catch (JsonSyntaxException e) {
                        Log.e("API Error", "Failed to parse user details", e);
                        runOnUiThread(() -> {
                            Toast.makeText(Planned_Meetings.this, "Failed to parse user details", Toast.LENGTH_SHORT).show();
                        });
                    }
                } else {
                    String responseBody = response.body().string();
                    Log.e("API Error", "Failed to fetch user details with status code " + response.code() + ": " + responseBody);
                    runOnUiThread(() -> {
                        Toast.makeText(Planned_Meetings.this, "Failed to load user details", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }

    private void fetchEvents() {
        fetchEvents(null); // Fetch all events if no date is provided
    }

    private void fetchEvents(String date) {
        if (email == null) {
            Toast.makeText(this, "User email not loaded yet. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://90.84.199.65:8000/events/";
        if (date != null) {
            url += "?date=" + date; // Adjust the URL to include the date parameter if provided
        }

        Request request = new Request.Builder()
                .url(url)
                .addHeader("accept", "application/json")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("API Error", "Failed to fetch events", e);
                runOnUiThread(() -> {
                    Toast.makeText(Planned_Meetings.this, "Failed to load events", Toast.LENGTH_SHORT).show();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    String responseBody = response.body().string();
                    Log.d("API Response", "Events response: " + responseBody);
                    List<Events> events = gson.fromJson(responseBody, new TypeToken<List<Events>>() {
                    }.getType());
                    List<Events> filteredEvents = new ArrayList<>();

                    for (Events event : events) {
                        if (event.getOrganizer_email().equals(email)) {
                            filteredEvents.add(event);
                        }
                    }

                    runOnUiThread(() -> {
                        EventsList.clear();
                        EventsList.addAll(filteredEvents);
                        eventAdapter.notifyDataSetChanged();
                    });
                } else {
                    String responseBody = response.body().string();
                    Log.e("API Error", "Failed to fetch events with status code " + response.code() + ": " + responseBody);
                    runOnUiThread(() -> {
                        Toast.makeText(Planned_Meetings.this, "Failed to load events", Toast.LENGTH_SHORT).show();
                    });
                }
            }
        });
    }


}
