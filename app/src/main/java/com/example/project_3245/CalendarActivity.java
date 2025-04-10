package com.example.project_3245;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CalendarActivity extends AppCompatActivity {

    private DatabaseReference databaseTasks;
    private List<Task> taskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        databaseTasks = FirebaseDatabase.getInstance().getReference("tasks");
        taskList = new ArrayList<>();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Calendar");
        }

        CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener((calendarView1, year, month, day) -> {
            String selectedDate = day + "/" + (month + 1) + "/" + year;
            queryTasksForDate(selectedDate);
        });
    }

    private void queryTasksForDate(String selectedDate) {
        databaseTasks.orderByChild("taskDate").equalTo(selectedDate).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskList.clear();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot taskSnapshot : dataSnapshot.getChildren()) {
                        Task task = taskSnapshot.getValue(Task.class);
                        String taskId = taskSnapshot.getKey();

                        if (task != null) {
                            task.setTaskId(taskId);
                            taskList.add(task);
                        }
                    }
                    showTaskDialog(selectedDate);
                } else {
                    Toast.makeText(CalendarActivity.this, "No tasks found for " + selectedDate, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(CalendarActivity.this, "Error fetching tasks", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showTaskDialog(String selectedDate) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Tasks for " + selectedDate);

        View dialogView = getLayoutInflater().inflate(R.layout.task_dialog, null);
        ListView listView = dialogView.findViewById(R.id.taskDialogListView);
        TaskAdapter taskAdapter = new TaskAdapter(this, taskList);
        listView.setAdapter(taskAdapter);

        builder.setView(dialogView)
                .setPositiveButton("Close", (dialog, id) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public class TaskAdapter extends ArrayAdapter<Task> {

        private final Context context;

        public TaskAdapter(Context context, List<Task> tasks) {
            super(context, 0, tasks);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.task_list_item, parent, false);
            }

            Task task = getItem(position);

            TextView taskNameTextView = convertView.findViewById(R.id.taskNameTextView);
            taskNameTextView.setText(task.getTaskName());

            ImageView infoIcon = convertView.findViewById(R.id.infoIcon);
            ImageView editIcon = convertView.findViewById(R.id.editIcon);
            ImageView deleteIcon = convertView.findViewById(R.id.deleteIcon);


            infoIcon.setOnClickListener(v -> {
                String taskName = task.getTaskName();
                String taskDate = task.getTaskDate();
                String taskTime = task.getTaskTime();
                String taskDescription = task.getTaskDescription();
                String taskPriority = task.getTaskPriority();

                Intent intent = new Intent(context, TaskInformation.class);

                intent.putExtra("taskName", taskName);
                intent.putExtra("taskDate", taskDate);
                intent.putExtra("taskTime", taskTime);
                intent.putExtra("taskDescription", taskDescription);
                intent.putExtra("taskPriority", taskPriority);

                context.startActivity(intent);
            });

            editIcon.setOnClickListener(v -> {
                String taskName = task.getTaskName();
                String taskDate = task.getTaskDate();
                String taskTime = task.getTaskTime();
                String taskDescription = task.getTaskDescription();
                String taskPriority = task.getTaskPriority();
                String taskId = task.getTaskId();

                Intent intent = new Intent(context, EditTaskInformation.class);

                intent.putExtra("taskName", taskName);
                intent.putExtra("taskDate", taskDate);
                intent.putExtra("taskTime", taskTime);
                intent.putExtra("taskDescription", taskDescription);
                intent.putExtra("taskPriority", taskPriority);
                intent.putExtra("taskId", taskId);

                context.startActivity(intent);

            });

            deleteIcon.setOnClickListener(v -> {
                deleteTask(task.getTaskId());
                finish();
            });

            return convertView;
        }

        private void deleteTask(String taskId) {
            DatabaseReference deleteTaskRef = FirebaseDatabase.getInstance().getReference("tasks").child(taskId);
            deleteTaskRef.removeValue().addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(CalendarActivity.this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(CalendarActivity.this, "Failed to delete task", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}

