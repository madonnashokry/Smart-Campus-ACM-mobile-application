package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class student_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_home);
        // Initialize buttons

        Button upcomingMeetingsButton = findViewById(R.id.upcoming);
        Button previousMeetingsButton = findViewById(R.id.pervv);
        ImageButton profilee = findViewById(R.id.male_user);
        Button lgfile = findViewById(R.id.log);
        Button attend = findViewById(R.id.trackk);

        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(student_home.this, Attendence_tracking.class);
                startActivity(intt);
            }
        });
        lgfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(student_home.this, logfilee.class);
                startActivity(intt);
            }

        });
        profilee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intt = new Intent(student_home.this, Profile.class);
                startActivity(intt);
            }
        });
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