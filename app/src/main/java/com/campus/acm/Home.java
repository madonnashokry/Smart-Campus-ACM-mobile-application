package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class Home extends AppCompatActivity {
    private AppCompatButton scheduleNewMeetingButton;
    private AppCompatButton upcomingMeetingsButton;
    private AppCompatButton plannedMeetingsButton;
    private AppCompatButton previousMeetingsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        scheduleNewMeetingButton = findViewById(R.id.schedulinggg);
        upcomingMeetingsButton = findViewById(R.id.cominggg);
        plannedMeetingsButton = findViewById(R.id.planned);
        previousMeetingsButton = findViewById(R.id.previous);
        scheduleNewMeetingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Scheduling.class);
                startActivity(intent);
            }
        });
        upcomingMeetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, upcoming_meetings.class);
                startActivity(intent);
            }
        });
        plannedMeetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this,Planned_Meetings.class);
                startActivity(intent);
            }
        });

        previousMeetingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Previous_Meetings.class);
                startActivity(intent);
            }
        });
    }
}

