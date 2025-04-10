package com.example.project_3245;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class TaskInformation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.task_information);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Task Information");
        }

        EditText taskNameEditText = findViewById(R.id.taskName);
        EditText taskDateEditText = findViewById(R.id.taskDate);
        EditText taskTimeEditText = findViewById(R.id.taskTime);
        EditText taskDescriptionEditText = findViewById(R.id.taskDescription);
        RadioGroup taskPriorityRadioGroup = findViewById(R.id.priorityGroup);

        Intent intent = getIntent();
        String taskName = intent.getStringExtra("taskName");
        String taskDate = intent.getStringExtra("taskDate");
        String taskTime = intent.getStringExtra("taskTime");
        String taskDescription = intent.getStringExtra("taskDescription");
        String taskPriority = intent.getStringExtra("taskPriority");


        taskNameEditText.setText(taskName);
        taskDateEditText.setText(taskDate);
        taskTimeEditText.setText(taskTime);
        taskDescriptionEditText.setText(taskDescription);

        if (taskPriority != null) {
            switch (taskPriority) {
                case "High":
                    taskPriorityRadioGroup.check(R.id.radioHigh);
                    break;
                case "Medium":
                    taskPriorityRadioGroup.check(R.id.radioMedium);
                    break;
                case "Low":
                    taskPriorityRadioGroup.check(R.id.radioLow);
                    break;
                default:
                    taskPriorityRadioGroup.clearCheck();
                    break;
            }
        }
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