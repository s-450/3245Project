package com.example.project_3245;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

// Testing if this was changed on GitHub
// Another test for this by Manav
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.calendar_page) {
            startActivity(new Intent(this, CalendarActivity.class));
            return true;
        } else if (id == R.id.expense_page) {
            startActivity(new Intent(this, ExpensesActivity.class));
            return true;
        } else if (id == R.id.tasks) {
            startActivity(new Intent(this, TasksActivity.class));
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}