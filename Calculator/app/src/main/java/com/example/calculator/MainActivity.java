package com.example.calculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText num1, num2, num3;

    TextView resultC;
    Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText) findViewById(R.id.number1);
        num2 = (EditText) findViewById(R.id.number2);
        num3 = (EditText) findViewById(R.id.number3);
        resultC = (TextView) findViewById(R.id.resultC);

        add = (Button) findViewById(R.id.add);
    }
    public void addition(View v){
        String value1 = num1.getText().toString();
        String value2 = num2.getText().toString();
        String value3 = num3.getText().toString();
        Integer valueF = (Integer.parseInt(value1)) + (Integer.parseInt(value2) + (Integer.parseInt(value3)));

        String result = valueF.toString();
        resultC.setText(result);
    }
}