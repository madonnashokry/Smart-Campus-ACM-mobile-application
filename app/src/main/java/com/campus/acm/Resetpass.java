package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Resetpass extends AppCompatActivity {


    //ViewFlipper ViewFlipper;
    EditText EmailEditText;
    EditText VerificationCode;
    EditText NewPassword;
    EditText ConfirmPassword;
    TextView mErrorTextView;
    Button contt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);

        NewPassword = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.confirmpassword);
        contt = findViewById(R.id.confrm);



        contt.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                String newPassword = NewPassword.getText().toString();
                String confirmPassword = ConfirmPassword.getText().toString();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    mErrorTextView.setText("Please fill in all fields.");
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    mErrorTextView.setText("Passwords do not match.");
                    return;
                }

                resetPassword(newPassword);
            }
        });
    }



    private void resetPassword(String newPassword) {
        OkHttpClient client = new OkHttpClient();

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String access_token = sharedPreferences.getString("access_token", "");
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/password-reset")
                .patch(RequestBody.create("{}", JSON))
                .addHeader("Authorization", "Bearer " + access_token)
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> mErrorTextView.setText("error to reset password."));
            }

            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(() -> {
                        mErrorTextView.setText("Password reset successful.");
                        // Optionally, navigate to another activity or clear the fields
                    });
                } else {
                    runOnUiThread(() -> mErrorTextView.setText("Failed to reset password. Please try again."));
                }
            }
        });
    }
}



        // retrieve the email address from the User class
     //   User user = User.User();
     //   String email = user.getEmail();



       /*
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
