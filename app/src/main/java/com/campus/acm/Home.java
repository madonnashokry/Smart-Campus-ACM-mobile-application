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
        scheduleNewMeetingButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Home.this, Scheduling.class);
                startActivity(intent);
            }
        });
    }

}