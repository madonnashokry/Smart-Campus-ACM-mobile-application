package com.campus.acm;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class notfications extends AppCompatActivity {
    public static final String CHANNEL_ID = "channel1";
   // public static final String CHANNEL_2_ID = "channel2";
    private static final int PERMISSION_REQUEST_CODE = 101;
    private int NOTIFICATION_ID = 1;
    private String textTitle = "Test Notification";
    private String textContent = "This is a test notification";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // EdgeToEdge.enable(this);
        setContentView(R.layout.activity_notfications);
        // Request the POST_NOTIFICATIONS permission
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                //.setSmallIcon(R.drawable.notification_icon)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

    }
}