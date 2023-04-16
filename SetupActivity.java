package com.example.smartmeter;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;

public class SetupActivity extends AppCompatActivity {

    private TextView tvHolidays;
    private ArrayList<String> holidayDates = new ArrayList<>();
    private boolean isHoliday = false; // Boolean variable to indicate if it's a holiday
    private Button btnAddDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setup);

        tvHolidays = findViewById(R.id.tvHoliday);
        btnAddDate = findViewById(R.id.btnAddDate);

        // Read holiday dates from text file
        readHolidayDates();

        // Get current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = sdf.format(calendar.getTime());
        Log.d("this is my array", "arr: " + holidayDates);
        // Check if current date is a holiday
        if (holidayDates.contains(currentDate)) {
            isHoliday = true; // Set isHoliday to true if current date is a holiday
            tvHolidays.setText("Today is a holiday.");
        } else {
            tvHolidays.setText("Today is not a holiday.");
        }

        // Add Date button click listener
        btnAddDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show date picker dialog
                showDatePickerDialog();
            }
        });
    }

    // Method to read holiday dates from a text file
    private void readHolidayDates() {
        // Code to read holiday dates from text file, as shown in previous example
        // ...
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            // Open the text file from assets folder
            inputStream = getAssets().open("Holidays.txt");
            inputStreamReader = new InputStreamReader(inputStream);
            bufferedReader = new BufferedReader(inputStreamReader);

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                // Add holiday dates to ArrayList
                holidayDates.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Close the streams and readers
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (inputStreamReader != null) {
                    inputStreamReader.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Method to show date picker dialog
    private void showDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // Append selected date to text file
                String selectedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", dayOfMonth, monthOfYear + 1, year);
                appendDateToTextFile(selectedDate);

                // Update UI
                tvHolidays.setText("Date added as holiday: " + selectedDate);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    // Method to append date to text file
    private void appendDateToTextFile(String date) {
        try {
            // Open the text file in append mode
            FileWriter fw = new FileWriter("holiday_dates.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(date + "\n"); // Append date with newline character
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getter method for isHoliday
    public boolean isHoliday() {
        return isHoliday;
}
}
























