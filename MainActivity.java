package com.example.smartmeter;
import android.content.Intent;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;


public class MainActivity extends AppCompatActivity {

    private TextView txtCurrentReadings;
    private TextView txtBillingAmount;
    private Button btnSetup;
    private Button btnSetLimits;


    // ThingSpeak API endpoints
    private static final String API_BASE_URL = "https://api.thingspeak.com";
    private static final String CHANNEL_ID = "1675435";
    private static final String READ_API_KEY = "ONB09N1GN6X4PP11";
    private static final String FIELD_CURRENT_READINGS = "2";
    private static final String FIELD_BILLING_AMOUNT = "3";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find TextViews by their respective IDs
        txtCurrentReadings = findViewById(R.id.txtCurrentReadings);
        txtBillingAmount = findViewById(R.id.txtBillingAmount);
        // Initialize UI elements
        btnSetLimits = findViewById(R.id.btnSetLimits);

        // Set click listener for the "Set Limits" button
        btnSetLimits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start LimitsActivity
                Intent intent = new Intent(MainActivity.this, LimitsActivity.class);
                startActivity(intent);
            }
        });
        btnSetup = findViewById(R.id.btnSetup);

        // Set click listener for "Setup" button
        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Start SetupActivity
                Intent intent = new Intent(MainActivity.this, SetupActivity.class);
                startActivity(intent);
            }
   });

        // Retrieve current readings and billing amount from ThingSpeak API
        getCurrentReadingsFromAPI();
        getBillingAmountFromAPI();
    }

    // Method to retrieve current readings from ThingSpeak API
    private void getCurrentReadingsFromAPI() {
        String url = API_BASE_URL + "/channels/" + CHANNEL_ID + "/fields/" + FIELD_CURRENT_READINGS + ".json?api_key=" + READ_API_KEY+"&results=2";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String currentReadings = response.getJSONArray("feeds")
                                .getJSONObject(0)
                                .getString(FIELD_CURRENT_READINGS);
                        txtCurrentReadings.setText(currentReadings);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }

    // Method to retrieve billing amount from ThingSpeak API
    private void getBillingAmountFromAPI() {
        String url = API_BASE_URL + "/channels/" + CHANNEL_ID + "/fields/" + FIELD_BILLING_AMOUNT + ".json?api_key=" + READ_API_KEY;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        String billingAmount = response.getJSONArray("feeds")
                                .getJSONObject(0)
                                .getString(FIELD_BILLING_AMOUNT);
                        txtBillingAmount.setText(billingAmount);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                Throwable::printStackTrace);

        // Add the request to the RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

    }
}