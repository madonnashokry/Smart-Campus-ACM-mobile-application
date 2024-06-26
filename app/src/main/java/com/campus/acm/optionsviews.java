package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class optionsviews extends AppCompatActivity {
   Button edtt;
   Button logev;
    int event_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_optionsviews);
        edtt = findViewById(R.id.edtttt);
        logev = findViewById(R.id.evetttlog);
        event_id = getIntent().getIntExtra("event_id", -1);
        edtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(optionsviews.this, Scheduling.class);
                intent.putExtra("event_id", event_id);
                startActivity(intent);
            }
        });
        logev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(optionsviews.this, event_loggg.class);
                intent.putExtra("event_id", event_id);
                startActivity(intent);
            }
        });

    }
    }
