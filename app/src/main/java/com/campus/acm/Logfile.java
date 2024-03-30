package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class Logfile extends AppCompatActivity {
    ImageButton backbtn ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //    EdgeToEdge.enable(this);
        //  setContentView(R.layout.activity_logfile);
        backbtn = findViewById(R.id.back);


        backbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i =new Intent(Logfile.this, Attendence_tracking.class);
                startActivity(i);
            }
        });
    }
}
