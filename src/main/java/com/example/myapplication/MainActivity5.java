package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity5 extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    EditText dollarEditor;
    EditText euroEditor;
    EditText wonEditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        Intent conf = getIntent();
        float dollar = conf.getFloatExtra("dollar_rate_key",0.0f);
        float euro = conf.getFloatExtra("euro_rate_key",0.0f);
        float won = conf.getFloatExtra("won_rate_key",0.0f);

        Log.i(TAG,"onCreate:dollar="+dollar);
        Log.i(TAG,"onCreate:euro="+euro);
        Log.i(TAG,"onCreate:won="+won);

        dollarEditor = findViewById(R.id.edit_dollar);
        euroEditor = findViewById(R.id.edit_euro);
        wonEditor = findViewById(R.id.edit_won);

        dollarEditor.setText(String.valueOf(dollar));
        euroEditor.setText(String.valueOf(euro));
        wonEditor.setText(String.valueOf(won));
    }
    public void save(View btn){
        float newDollar = Float.parseFloat(dollarEditor.getText().toString());
        float newEuro = Float.parseFloat(euroEditor.getText().toString());
        float newWon = Float.parseFloat(wonEditor.getText().toString());

        Intent intent = getIntent();
        Bundle bdl = new Bundle();
        bdl.putFloat("key_dollar",newDollar);
        bdl.putFloat("key_euro",newEuro);
        bdl.putFloat("key_won",newWon);
        intent.putExtras(bdl);
        setResult(2,intent);
        finish();
    }
}