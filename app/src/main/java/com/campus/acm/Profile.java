package com.campus.acm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Profile extends AppCompatActivity {
    private TextView profileText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private EditText passwordEditText;
    private EditText currentPasswordEditText;
    private EditText newPasswordEditText;
    private EditText confirmPasswordEditText;
    Button changePasswordButton ;
    Button saveButton ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        profileText = findViewById(R.id.profile_text);
        firstNameEditText = findViewById(R.id.first_name_edittext);
        lastNameEditText = findViewById(R.id.last_name_edittext);
        passwordEditText = findViewById(R.id.password_edittext);
       changePasswordButton = findViewById(R.id.changepass);

     saveButton = findViewById(R.id.svebutton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText( Profile.this, "Your data is set successfully", Toast.LENGTH_SHORT).show();
            }
        });
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Resetpass activity
                startActivity(new Intent(Profile.this, Resetpass.class));
            }


        });
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", "");

}
/*
    public void fetchUserDetailsById(String userId) {
        SharedPreferences sharedPreferenc = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        String access_token = sharedPreferenc.getString("acess_token", "");

        OkHttpClient client = new OkHttpClient();
        String url = "http://90.84.199.65:8000/user/" + userId;

        Request request = new Request.Builder()
                .url(url)
                .addHeader("Authorization", "Bearer " + access_token) // Retrieve accessToken from shared preferences
                .addHeader("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(() -> Toast.makeText(Profile.this, "Error fetching user details: " + e.getMessage(), Toast.LENGTH_SHORT).show());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() -> Toast.makeText(Profile.this, "Failed to fetch user details", Toast.LENGTH_SHORT).show());
                    return;
                }

                String responseBody = response.body().string();
                try {
                    JSONObject jsonResponse = new JSONObject(responseBody);
                    // Assuming the response contains user details like first name, last name, etc.
                    String firstName = jsonResponse.getString("first_name");
                    String username = jsonResponse.getString()
                    String lastName = jsonResponse.getString("last_name");
                    // Update UI elements with user details
                    runOnUiThread(() -> {
                        firstNameEditText.setText(firstName);
                        lastNameEditText.setText(lastName);
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                    runOnUiThread(() -> Toast.makeText(Profile.this, "Error parsing user details JSON.", Toast.LENGTH_SHORT).show());
                }
            }
        });
    }

*/

}
