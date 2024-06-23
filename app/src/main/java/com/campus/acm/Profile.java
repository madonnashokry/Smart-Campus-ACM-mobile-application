package com.campus.acm;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

    String accessToken;


    private TextView firstNameTextView;
    private TextView roleTextView;
    private TextView idTextView;
    private TextView emailTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize views
        profileText = findViewById(R.id.profile_text);

       changePasswordButton = findViewById(R.id.changepass);
        firstNameTextView = findViewById(R.id.fsttlet);
        roleTextView = findViewById(R.id.rolllet);
        idTextView = findViewById(R.id.iddget);
        emailTextView = findViewById(R.id.emmlet);


        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start Resetpass activity
                startActivity(new Intent(Profile.this, SignInActivity.class));
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPreferences", MODE_PRIVATE);
        accessToken = sharedPreferences.getString("access_token", "");
        fetchUserDetails();

}
    private void fetchUserDetails() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://90.84.199.65:8000/user/info")
                .addHeader("Authorization", "Bearer " + accessToken)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Profile", "API call failed: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String responseData = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                JSONObject jsonObject = new JSONObject(responseData);
                                String id = jsonObject.getString("id");
                                String firstName = jsonObject.getString("first_name");
                                String email = jsonObject.getString("email");
                                String role = jsonObject.getString("role");

                                idTextView.setText(id);
                                firstNameTextView.setText(firstName);
                                emailTextView.setText(email);
                                roleTextView.setText(role);
                            } catch (JSONException e) {
                                Log.e("Profile", "JSON parsing error: " + e.getMessage());
                            }
                        }
                    });
                } else {
                    Log.e("Profile", "fetching unsuccessful: " + response.code());
                }
            }
        });
    }
}


