package com.example.smartmeter;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class LimitsActivity extends AppCompatActivity {

    private EditText etLimitValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_limits);

        // Initialize UI elements
        etLimitValue = findViewById(R.id.etLimitValue);
        Button btnSaveLimits = findViewById(R.id.btnSaveLimits);

        // Set click listener for the "Save Limits" button
        btnSaveLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String limitValue = etLimitValue.getText().toString();
                if (!limitValue.isEmpty()) {
                    int limit = Integer.parseInt(limitValue);
                    int txtBillingAmount = 100; // Replace with your actual logic to get the current billing amount

                    // Check if current billing amount is equal to half of the limit
                    if (txtBillingAmount == (limit / 2)) {
                        // Call a method to send notification
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            sendNotification(limit);
                        }
                    }

                    // Redirect to MainActivity
                    Intent intent = new Intent(LimitsActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Finish the LimitsActivity
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void sendNotification(int limit) {
        // For demonstration purposes, we'll just show a simple notification
        // when the condition is met
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("Bill Limit Reached")
                .setContentText("You have consumed 50% of your bill limit")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, builder.build());
}
}