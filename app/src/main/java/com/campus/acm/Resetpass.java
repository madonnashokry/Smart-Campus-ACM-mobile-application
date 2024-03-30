package com.campus.acm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ViewFlipper;


import java.util.Random;

public class Resetpass extends AppCompatActivity {


     ViewFlipper ViewFlipper;
       EditText EmailEditText;
      EditText VerificationCode;
      EditText NewPassword;
     EditText ConfirmPassword;
     TextView mErrorTextView;
     Button reset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);
        ViewFlipper = findViewById(R.id.view_flipper);
     //  mEmailEditText = findViewById(R.id.email_edit_text);
        VerificationCode = findViewById(R.id.verif_code);
        NewPassword = findViewById(R.id.new_password_edit_text);
        ConfirmPassword = findViewById(R.id.confirm_password_edit_text);
       // mErrorTextView = findViewById(R.id.error_text_view);
        reset =findViewById(R.id.resetbtn);


        // retrieve the email address from the User class
     //   User user = User.User();
     //   String email = user.getEmail();



        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String verif = VerificationCode.getText().toString();
                String newPass = NewPassword.getText().toString();
                String confirmPass = ConfirmPassword.getText().toString();
                if (verif.isEmpty() || newPass.isEmpty() || confirmPass.isEmpty()) {
                    mErrorTextView.setText("Please fill in all fields");
                    return;
                }

                if (!newPass.equals(confirmPass)) {
                    mErrorTextView.setText("Passwords do not match");
                    return;
                }

                // Send a request to the server to reset the user's password
            }
        });
    }

}
   /* String generateVerificationCode() {
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



   private boolean verifyCode(String enteredVerificationCode) {
       // User user = User.getCurrentUser();
      //  String verificationCode = user.getVerificationCode();      //getter from user class
     //   return enteredVerificationCode.equals(verificationCode);
        return false;
    }
    void resetPassword(String newPassword) {
        // Retrieve the email address from the User class
     //   User user = User.getCurrentUser();
      //  String email = user.getEmail();

        // Reset the user's password in the database

        // API to reset the password

        // Remove the verification code from the User object
      //  user.setVerificationCode(null);
    }

   void sendVerfccode(){

     }

   */