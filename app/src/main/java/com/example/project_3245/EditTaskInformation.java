package com.example.project_3245;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditTaskInformation extends AppCompatActivity {

    private EditText taskNameEditText, taskDateEditText, taskTimeEditText, taskDescriptionEditText;
    private RadioGroup taskPriorityRadioGroup;

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_task_information);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Edit Task");
        }

        taskNameEditText = findViewById(R.id.taskName);
        taskDateEditText = findViewById(R.id.taskDate);
        taskTimeEditText = findViewById(R.id.taskTime);
        taskDescriptionEditText = findViewById(R.id.taskDescription);
        taskPriorityRadioGroup = findViewById(R.id.priorityGroup);

        Intent intent = getIntent();
        String taskId = intent.getStringExtra("taskId");
        String taskName = intent.getStringExtra("taskName");
        String taskDate = intent.getStringExtra("taskDate");
        String taskTime = intent.getStringExtra("taskTime");
        String taskDescription = intent.getStringExtra("taskDescription");
        String taskPriority = intent.getStringExtra("taskPriority");

        db = FirebaseDatabase.getInstance().getReference();

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
            }
        }

        Button saveButton = findViewById(R.id.editTaskBtn);
        saveButton.setOnClickListener(v -> {
            String updatedTaskName = taskNameEditText.getText().toString();
            String updatedTaskDate = taskDateEditText.getText().toString();
            String updatedTaskTime = taskTimeEditText.getText().toString();
            String updatedTaskDescription = taskDescriptionEditText.getText().toString();

            int selectedPriorityId = taskPriorityRadioGroup.getCheckedRadioButtonId();
            String updatedTaskPriority = null;

            if (selectedPriorityId == R.id.radioHigh) {
                updatedTaskPriority = "High";
            } else if (selectedPriorityId == R.id.radioMedium) {
                updatedTaskPriority = "Medium";
            } else if (selectedPriorityId == R.id.radioLow) {
                updatedTaskPriority = "Low";
            }

            if (updatedTaskName.isEmpty() || updatedTaskDate.isEmpty() || updatedTaskTime.isEmpty() || updatedTaskDescription.isEmpty() || updatedTaskPriority == null) {
                Toast.makeText(EditTaskInformation.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            Task updatedTask = new Task(updatedTaskName, updatedTaskDescription, updatedTaskDate, updatedTaskTime, updatedTaskPriority);

            db.child("tasks").child(taskId).setValue(updatedTask)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EditTaskInformation.this, "Task updated successfully", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();
                    })
                    .addOnFailureListener(e -> Toast.makeText(EditTaskInformation.this, "Failed to update task", Toast.LENGTH_SHORT).show());
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
