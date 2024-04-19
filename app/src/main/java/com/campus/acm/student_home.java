package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
<<<<<<< HEAD
import android.widget.Button;
import android.widget.ImageButton;
=======
>>>>>>> a708edb051a13db4893cf4fa63474c1e81c92dd3

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class student_home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student_home);
        // Initialize buttons
<<<<<<< HEAD
        Button upcomingMeetingsButton = findViewById(R.id.upcoming);
        Button previousMeetingsButton = findViewById(R.id.pervv);
        ImageButton profilee = findViewById(R.id.male_user);
=======
        AppCompatButton upcomingMeetingsButton = findViewById(R.id.upcoming);
        AppCompatButton previousMeetingsButton = findViewById(R.id.pervv);
>>>>>>> a708edb051a13db4893cf4fa63474c1e81c92dd3


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
