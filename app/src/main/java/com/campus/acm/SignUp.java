package com.campus.acm;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.campus.acm.SecurePreferencesHelper;
import com.campus.acm.Token;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.GeneralSecurityException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SignUp extends AppCompatActivity {
    String generated_token;
    EditText etFirstName, etMiddleName, etLastName, etSSN, etPassword, etEmail;
    Button btnSignUp;
    TextView tvAlreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etFirstName = findViewById(R.id.sign_up_username);
        etMiddleName = findViewById(R.id.middle_name);
        etLastName = findViewById(R.id.last_name);
        etEmail = findViewById(R.id.sign_up_email);
        etSSN = findViewById(R.id.SSN);
        etPassword = findViewById(R.id.pass);
        btnSignUp = findViewById(R.id.login_btn);

        // Set an OnClickListener for the Sign Up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String f_name = etFirstName.getText().toString();
                String m_name = etMiddleName.getText().toString();
                String l_name = etLastName.getText().toString();
                String email = etEmail.getText().toString();
                String ssn = etSSN.getText().toString();
                String password = etPassword.getText().toString();

                // Validate the input here

                // Call the createUser() method to send the data to the server
                try {
                    createUser(f_name, m_name, l_name, email, ssn, password);
                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Sign up failed: Error sending request", Toast.LENGTH_SHORT).show();
                } catch (GeneralSecurityException e) {
                    throw new RuntimeException(e);
                }

                Intent i = new Intent(SignUp.this, SignInActivity.class);
                startActivity(i);
            }
        });
    }

    private void createUser(String f_name, String m_name, String l_name, String email, String ssn, String password) throws JSONException, IOException, GeneralSecurityException {
        JSONObject userData = new JSONObject();
        userData.put("f_name", f_name);
        userData.put("m_name", m_name);
        userData.put("l_name", l_name);
        userData.put("ssn", ssn);
        userData.put("email", email);
        userData.put("password", password);

        // TODO: generate the token here
        generated_token = Token.generate_token();
        // TODO: ADD the token to the json object
        userData.put("token", generated_token);

        doPostRequest(userData);
    }

    private void doPostRequest(JSONObject userData) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://90.84.199.65:8000/signup";
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        RequestBody body = RequestBody.create(JSON, userData.toString());

        Request newReq = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(newReq).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "Sign up failed: Error sending request", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO: call the set token function in shared pref
                            SecurePreferencesHelper.setToken(getApplicationContext(),generated_token);
                            Toast.makeText(getApplicationContext(), "Sign up successful", Toast.LENGTH_SHORT).show();
                            Toast.makeText(getApplicationContext(), "TOKEN is generated successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SignUp.this, SignInActivity.class);
                            startActivity(intent);
                        }
                    });
                } else {
                    final String errorBody = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sign up failed: " + errorBody, Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}