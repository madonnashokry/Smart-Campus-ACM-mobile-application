package com.campus.acm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;
public class SignUp extends AppCompatActivity {


    EditText etName, etUniversityID, etSSN, etPassword, etConfirmPassword, email;
    Button btnSignUp;
    TextView tvAlreadyHaveAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        etName = findViewById(R.id.sign_up_username);
        etUniversityID = findViewById(R.id.ID);
        email = findViewById(R.id.sign_up_email);
        etSSN = findViewById(R.id.SSN);
        etPassword = findViewById(R.id.pass);
        etConfirmPassword = findViewById(R.id.confpass);
        btnSignUp = findViewById(R.id.login_btn);
        //tvAlreadyHaveAccount = findViewById(R.id.tv_already_have_account);

        // Set an OnClickListener for the Sign Up button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String universityID = etUniversityID.getText().toString();
                String ssn = etSSN.getText().toString();
                String password = etPassword.getText().toString();
                String confirmPassword = etConfirmPassword.getText().toString();

                // Validate the input here

                // Call the createUser() method to send the data to the server
                createUser();
            }
        });

    }

    private void createUser() {
        // Prepare the user data as a Map<String, String>
        Map<String, String> userData = new HashMap<>();
        userData.put("name", etName.getText().toString());
        userData.put("email", email.getText().toString());
        //ADD ANOTHER PARAMETERS OF USER
        //USING API`S TO CREAT USER

}


}