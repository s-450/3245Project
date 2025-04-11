package com.example.project_3245;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ExpensesActivity extends AppCompatActivity {

    EditText num1, num2, num3, editTextDate;

    TextView resultC;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.black));

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle("Expenses");
        }

        num1 = (EditText) findViewById(R.id.number1);
        num2 = (EditText) findViewById(R.id.number2);
        num3 = (EditText) findViewById(R.id.number3);
        editTextDate = findViewById(R.id.editTextDate);
        resultC = (TextView) findViewById(R.id.resultC);

        add = (Button) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dueDate = editTextDate.getText().toString();
                String value1 = num1.getText().toString();
                String value2 = num2.getText().toString();
                String value3 = num3.getText().toString();

                int valueF = Integer.parseInt(value1) + Integer.parseInt(value2) + Integer.parseInt(value3);
                String result = String.valueOf(valueF);
                resultC.setText(result);

                // Get a reference to the Firebase Realtime Database
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference("expenses");
                Expense expense = new Expense(dueDate, result);
                // Push the data to the database
                myRef.push().setValue(expense)
                        .addOnSuccessListener(aVoid -> {
                            // Data written successfully
                            Toast.makeText(getApplicationContext(), "Data saved", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            // Failed to write data
                            Toast.makeText(getApplicationContext(), "Failed to save data", Toast.LENGTH_SHORT).show();
                        });
            }
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

    public void totalValue(View v){
        String value1 = num1.getText().toString();
        String value2 = num2.getText().toString();
        String value3 = num3.getText().toString();
        Integer valueF = (Integer.parseInt(value1) + Integer.parseInt(value2) + Integer.parseInt(value3));

        String result = valueF.toString();
        resultC.setText(result);
    }
}
