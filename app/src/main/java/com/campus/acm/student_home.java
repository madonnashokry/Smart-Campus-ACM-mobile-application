package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class student_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_home);
        // Initialize buttons
        AppCompatButton upcomingMeetingsButton = findViewById(R.id.upcoming);
        AppCompatButton previousMeetingsButton = findViewById(R.id.pervv);

        // Set click listeners
        upcomingMeetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_home.this, upcoming_meetings.class));
            }
        });

        previousMeetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(student_home.this, Previous_Meetings.class));
            }
        });
    }
    }
