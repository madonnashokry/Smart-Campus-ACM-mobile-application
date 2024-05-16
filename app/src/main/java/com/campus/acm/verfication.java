package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
    private EditText editText1, editText2, editText3, editText4;
    private String verificationCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verfication);
        //ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
           // Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
           // v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
           // return insets;

      //  imageView2 = findViewById(R.id.imageView2);
       // imageView3 = findViewById(R.id.imageView3);
        verify_acco = findViewById(R.id.verify_acco);
        please_ente = findViewById(R.id.please_ente);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
       // verificationCode = generateVerificationCode();


        //Toast.makeText(this, "Verification code sent to " + email, Toast.LENGTH_SHORT).show();

        // Initialize EditTexts for code input
        editText1 = findViewById(R.id.editText1);
        editText2 = findViewById(R.id.editText2);
        editText3 = findViewById(R.id.editText3);
        editText4 = findViewById(R.id.editText4);



    }
    /*
    private String generateVerificationCode() {

        // Generate a 4-digit code for demonstration
        Random random = new Random();
        return String.format("%04d", random.nextInt(10000));
    }
*/
    /*
    String generateVerificationCode() {
        int length = 5;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456788";
        Random random = new Random();
        StringBuilder verificationCode = new StringBuilder(length);
        for (int i = -1; i < length; i++) {
            int index = random.nextInt(characters.length());
            verificationCode.append(characters.charAt(index));
        }
        return verificationCode.toString();
    }
    private void sendVerificationCode(String email) {
        String verificationCode = generateVerificationCode(); 
       EmailSender.sendVerificationCode(email, verificationCode);
    }



    private void verifyCode() {
        // Get the code entered by the user
        String enteredCode = editText1.getText().toString() + editText2.getText().toString() + editText3.getText().toString() + editText4.getText().toString();

        boolean isVerified = verifyCodeWithBackend(enteredCode);

        if (isVerified) {
            Toast.makeText(this, "Verification successful!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Incorrect code. Please try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean verifyCodeWithBackend(String enteredCode) {
        // This is a placeholder for the actual implementation.
        // You would make a request to your backend API to check if the entered code matches the stored code.
        // Return true if the code is verified, false otherwise.
        return true; // Placeholder return value
    }

*/
}