package com.example.project_3245;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Tasks");
        }

        EditText editTextDate = findViewById(R.id.taskDate);
        ImageView DatePickerIcon = findViewById(R.id.datePickerIcon);

        DatePickerIcon.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    TasksActivity.this,
                    (view, selectedYear, selectedMonth, selectedDay) -> {
                        String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                        editTextDate.setText(date);
                    },
                    year, month, day);
            datePickerDialog.show();
        });


        EditText editTextTime = findViewById(R.id.taskTime);
        ImageView timePickerIcon = findViewById(R.id.timePickerIcon);

        timePickerIcon.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    TasksActivity.this,
                    (view, selectedHour, selectedMinute) -> {
                        String time = selectedHour + ":" + selectedMinute;
                        editTextTime.setText(time);
                    },
                    hour, minute, true);
            timePickerDialog.show();
        });


        EditText taskName = findViewById(R.id.taskName);
        EditText taskDescription = findViewById(R.id.taskDescription);
        RadioGroup taskPriority = findViewById(R.id.priorityGroup);
        Button buttonAddTask = findViewById(R.id.taskBtn);


        DatabaseReference databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");

        buttonAddTask.setOnClickListener(v -> {

            String name = taskName.getText() != null ? taskName.getText().toString().trim() : "";
            String description = taskDescription.getText() != null ? taskDescription.getText().toString().trim() : "";
            String date = editTextDate.getText() != null ? editTextDate.getText().toString().trim() : "";
            String time = editTextTime.getText() != null ? editTextTime.getText().toString().trim() : "";


            if (TextUtils.isEmpty(name)) {
                taskName.setError("Task name is required");
                taskName.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(description)) {
                taskDescription.setError("Task description is required");
                taskDescription.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(date)) {
                editTextDate.setError("Task date is required");
                editTextDate.requestFocus();
                return;
            }

            if (TextUtils.isEmpty(time)) {
                editTextTime.setError("Task time is required");
                editTextTime.requestFocus();
                return;
            }

            String priority = "";
            int selectedId = taskPriority.getCheckedRadioButtonId();
            if (selectedId == R.id.radioHigh) {
                priority = "High";
            } else if (selectedId == R.id.radioMedium) {
                priority = "Medium";
            } else if (selectedId == R.id.radioLow) {
                priority = "Low";
            }

            if (TextUtils.isEmpty(priority)) {
                Toast.makeText(TasksActivity.this, "Please select a priority", Toast.LENGTH_SHORT).show();
                return;
            }

            Task task = new Task(name, description, date, time, priority);

            databaseTasks.push().setValue(task)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(TasksActivity.this, "Task added successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(TasksActivity.this, "Failed to add task: " + e.getMessage(), Toast.LENGTH_SHORT).show());

        });
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}