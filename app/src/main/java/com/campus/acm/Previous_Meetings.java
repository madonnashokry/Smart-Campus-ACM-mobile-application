package com.campus.acm;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Previous_Meetings extends AppCompatActivity {
    TextView text1 ;
    TextView text2;
    TextView text3;
    TextView text4;
    TextView text5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_previous_meetings);
      //  View view1 = findViewById(R.layout.rectangle_5_ek2);
        // text1  = findViewById(R.layout.meet1);

    }
}