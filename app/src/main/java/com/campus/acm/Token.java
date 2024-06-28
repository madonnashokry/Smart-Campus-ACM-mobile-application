package com.campus.acm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Token extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_token);
        TextView token_text = findViewById(R.id.token_text);
        Button generateButton = findViewById(R.id.generate_token_button);
        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    generate_token();
                } catch (GeneralSecurityException | IOException e) {
                    throw new RuntimeException(e);
                }
                token_text.setText(SecurePreferencesHelper.getToken(getApplicationContext()));

            }
        });
    }


    public static String generate_token () throws GeneralSecurityException, IOException {
        SecureRandom random = new SecureRandom();
        long csprng =  random.nextLong() % (long) 1e50;
        byte [] bytes_csprng = Long.toString(csprng).getBytes();
        String sha256Hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = digest.digest(bytes_csprng);
            sha256Hash = bytesToHex(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha256Hash;



    }

    private static String bytesToHex(byte[] hashedBytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : hashedBytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }

}


