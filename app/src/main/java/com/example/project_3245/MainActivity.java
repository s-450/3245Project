package com.example.project_3245;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.CalendarView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((calendarView1, year, month, day) -> {
            String date = day + "/" + month + "/" + year;
            Toast.makeText(MainActivity.this, date, Toast.LENGTH_SHORT).show();
            System.out.println(date);
        });
    }
}