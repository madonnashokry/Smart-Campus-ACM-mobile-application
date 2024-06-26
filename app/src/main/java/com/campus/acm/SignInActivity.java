package com.campus.acm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignInActivity extends AppCompatActivity {
    EditText email;
    EditText Password;
    Button signin;
    Button signup;
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    Button forgetpass;

    private String Email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        signin = findViewById(R.id.login_btn);
        signup = findViewById(R.id.signup_btn);
        forgetpass = findViewById(R.id.sign_in_forget_password);


        email = findViewById(R.id.signin_email);

        Password = findViewById(R.id.sign_in_password);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(SignInActivity.this, SignUp.class);
                startActivity(inte);
            }
        });

        forgetpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent inte = new Intent(SignInActivity.this, Resetpass.class);
                startActivity(inte);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userlogin(); // Call the method to handle user login
              //  Intent in = new Intent(SignInActivity.this, student_home.class);
                //fetchUserDetails();
            }
        });
    }


    public void userlogin() {
        String username = email.getText().toString();
        String password = Password.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Both username and password are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create the request body as form data
        RequestBody formBody = new FormBody.Builder()
                .add("grant_type", "") // Empty
                .add("username", username)
                .add("password", password)
                .add("scope", "") // Empty
                .add("client_id", "") // Empty
                .add("client_secret", "") // Empty
                .build();

        // Build the request
        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/login")
                .post(formBody)
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addHeader("Accept", "application/json")
                .build();

        // Create an OkHttp client
        OkHttpClient client = new OkHttpClient();

        // Execute the request
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                // Handle failure
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(SignInActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                // Check if the response is successful

                if (!response.isSuccessful()) {
                    // Handle unsuccessful response
                    runOnUiThread(() -> Toast.makeText(SignInActivity.this, "Login failed", Toast.LENGTH_SHORT).show());
                    return;
                }

                // Handle successful response
                String responseBody = response.body().string();

                // Assuming the response is a JSON object, parse it
                try {
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String access_token = jsonResponse.getString("access_token");
                    Log.d("FetchUserDetails", "Access Token: " + access_token);
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("access_token", access_token);
                    editor.apply();
                    //Log.d("FetchUserDetails", "Access Token: " + access_token);

                    //if login is success
                    runOnUiThread(() -> Toast.makeText(SignInActivity.this, "Login successful.", Toast.LENGTH_SHORT).show());
                    //Intent intent = new Intent(SignInActivity.this, student_home.class);
                    //intent.putExtra("access_token", accessToken);
                    //startActivity(intent);
                    fetchUserDetails();

                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(SignInActivity.this, "Error parsing JSON from response.", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

    public void fetchUserDetails() {
        // Retrieve the access token from shared preferences
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("access_token", "");

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/user/info")
                .addHeader("Authorization", "Bearer " + accessToken)
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(SignInActivity.this, "Error fetching user details: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(SignInActivity.this, "Failed to fetch user details", Toast.LENGTH_SHORT).show());
                    return;
                }

                String responseBody = response.body().string();
                try {
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    String role = jsonResponse.getString("role");
                    String email = jsonResponse.getString("email");

                    // Redirect based on the user's role
                    Intent intent;
                    if ("student".equalsIgnoreCase(role)) {
                        intent = new Intent(SignInActivity.this, student_home.class);
                    } else if ("staff".equalsIgnoreCase(role) || "ta".equalsIgnoreCase(role) || "professor".equalsIgnoreCase(role) || "dean".equalsIgnoreCase(role) || "vice dean".equalsIgnoreCase(role)) {
                        intent = new Intent(SignInActivity.this, Home.class);
                    } else {
                        runOnUiThread(() -> Toast.makeText(SignInActivity.this, "Unknown user role", Toast.LENGTH_SHORT).show());
                        return;
                    }
                    // Pass the email address to the verification activity
                    intent.putExtra("email", email);

                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(SignInActivity.this, "Error parsing user details JSON.", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }





}
