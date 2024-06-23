package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class verfication extends AppCompatActivity {
    private ImageView imageView2;
    private ImageView imageView3;
    private TextView verify_acco;
    private TextView please_ente;
    private View view1;
    private View view2;
    private View view3;
    private View view4;
    private EditText editText1, editText2, editText3, editText4;
    private String verificationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verfication);

        verify_acco = findViewById(R.id.verify_acco);
        please_ente = findViewById(R.id.please_ente);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");

        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);

        sendVerificationCode(email);
    }

    private String generateVerificationCode() {
        Random random = new Random();
        verificationCode = String.valueOf(random.nextInt(9000) + 1000); // Generates a 4-digit code
        return verificationCode;
    }

    private void sendVerificationCode(String email) {
        generateVerificationCode();
        String subject = "Your Verification Code";
        String message = "Your verification code is: " + verificationCode;
        //JavaMailAPI javaMailAPI = new JavaMailAPI(this, email, subject, message);
        //javaMailAPI.execute();
        Toast.makeText(this, "Verification code sent to " + email, Toast.LENGTH_SHORT).show();
    }

    private void verifyCode() {
        String enteredCode = editText1.getText().toString() + editText2.getText().toString() +
                editText3.getText().toString() + editText4.getText().toString();

        if (enteredCode.equals(verificationCode)) {
            Toast.makeText(this, "Verification successful!", Toast.LENGTH_SHORT).show();
            // Proceed to the next activity or process
        } else {
            Toast.makeText(this, "Incorrect code. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onVerifyButtonClick(View view) {
        verifyCode();
    }
}
