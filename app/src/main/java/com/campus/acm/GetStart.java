package com.campus.acm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GetStart extends AppCompatActivity {

        Button getStarted;
        TextView txt;

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_get_start);
            getStarted = findViewById(R.id.get_started_main_btn);
            txt = findViewById(R.id.welcome_to_);

            getStarted.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    startActivity(new Intent(GetStart.this, SignInActivity.class));
                }
            });
        }
    }