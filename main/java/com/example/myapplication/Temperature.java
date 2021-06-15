package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;

public class Temperature extends AppCompatActivity {

    TextView out;
    EditText edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature);

        out = (TextView)findViewById(R.id.result);
        edit = (EditText)findViewById(R.id.tem1);

        Button but = (Button)findViewById(R.id.tem2);
        but.setOnClickListener((View.OnClickListener) this);
    }

    public void showResult(View v) throws UnsupportedEncodingException {
        Log.i("main","onClick msg");
        String str = edit.getText().toString();
        Double num = 22+Double.parseDouble(str)*1.8;
        out.setText("结果为："+num);

    }
}