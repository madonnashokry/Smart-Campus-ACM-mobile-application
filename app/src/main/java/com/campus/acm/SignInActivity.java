package com.campus.acm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.campus.acm.R;
import com.campus.acm.Resetpass;
import com.campus.acm.SignUp;

public class SignInActivity extends AppCompatActivity {
    TextView email ;
    TextView password ;
    Button signin ;
    Button signup ;

    Button forgetpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signin = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signup_btn);
        forgetpass = findViewById(R.id.sign_in_forget_password);
        //TextView dont = findViewById(R.id.donthave);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this, SignUp.class);
                startActivity(i);
            }
        });


        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(SignInActivity.this, Resetpass.class);
                startActivity(i);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                email = findViewById(R.id.signin_email);
                String em = email.getText().toString();
                password = findViewById(R.id.sign_in_password);
                String pass = password.getText().toString();
                //check if the user has acc according to API`S

            }
        });


    }





}