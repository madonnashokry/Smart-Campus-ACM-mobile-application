package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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
            startActivity(new Intent(Logfile.this, Attendence_tracking.class));
        }
    });
    }
}
