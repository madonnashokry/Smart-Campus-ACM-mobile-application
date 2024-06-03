package com.campus.acm;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Resetpass extends AppCompatActivity {


    //ViewFlipper ViewFlipper;
    EditText EmailEditText;
    EditText VerificationCode;
    EditText NewPassword;
    EditText ConfirmPassword;
    //TextView mErrorTextView;
    Button contt;

    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    OkHttpClient client = new OkHttpClient();
    private static final String TAG = "Resetpass";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetpass);

        NewPassword = findViewById(R.id.password);
        ConfirmPassword = findViewById(R.id.confirmpassword);
        contt = findViewById(R.id.confrm);




        contt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newPassword = NewPassword.getText().toString();
                String confirmPassword = ConfirmPassword.getText().toString();

                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(Resetpass.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    Toast.makeText(Resetpass.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    resetPassword(newPassword);
                } catch (Exception e) {
                    Log.e(TAG, "Error during password reset", e);
                    Toast.makeText(Resetpass.this, "An error occurred while resetting the password.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void resetPassword(String newPassword) {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("access_token", "");

        if (accessToken == null || accessToken.isEmpty()) {
            runOnUiThread(() -> Toast.makeText(Resetpass.this, "No access token found. Please log in again.", Toast.LENGTH_SHORT).show());
            return;
        }

        //String json = "{}";
        String json = "{\"new_password\": \"" + newPassword + "\"}";


        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/password-reset")
                .patch(body)
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Network request failed", e);
                runOnUiThread(() -> Toast.makeText(Resetpass.this, "Error resetting password. Please check your network connection.", Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Password reset successful");
                    runOnUiThread(() -> Toast.makeText(Resetpass.this, "Password reset successfully.", Toast.LENGTH_SHORT).show());
                    // Optionally, navigate to another activity or clear the fields
                } else {
                    int responseCode = response.code();
                    String responseBody = response.body() != null ? response.body().string() : "Unknown error";
                    Log.e(TAG, "Failed to reset password. Error code: " + responseCode + ". Message: " + responseBody);
                    runOnUiThread(() -> Toast.makeText(Resetpass.this, "Failed to reset password. Error code: " + responseCode + ". Message: " + responseBody, Toast.LENGTH_LONG).show());
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
