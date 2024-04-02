package com.campus.acm;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class verfication extends AppCompatActivity {
    private ImageView imageView2;
    private ImageView imageView3;
    private TextView verify_acco;
    private TextView please_ente;
    private View view1;
    private View view2;
    private View view3;
    private View view4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verfication);
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
           // Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
           // v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
           // return insets;
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        verify_acco = findViewById(R.id.verify_acco);
        please_ente = findViewById(R.id.please_ente);



    }
}