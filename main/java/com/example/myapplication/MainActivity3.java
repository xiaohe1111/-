package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity3 extends AppCompatActivity {

    TextView score;
    TextView score2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        score = (TextView) findViewById(R.id.score);
        score2 = (TextView) findViewById(R.id.score2);
    }
    public void btnClick1(View btn){
        if(btn.getId()==R.id.button1){
            showScore(1);
        }else{
            showScore2(1);
        }
    }
    public void btnClick2(View btn){
        if(btn.getId()==R.id.button2){
            showScore(2);
        }else{
            showScore2(2);
        }
    }
    public void btnClick3(View btn){
        if(btn.getId()==R.id.button3){
            showScore(3);
        }else{
            showScore2(3);
        }
    }
    public void btnReset(View btn){
        score.setText("0");
        score2.setText("0");
    }
    private void showScore(int inc){
        Log.i("show","inc="+inc);
        String oldscore = (String)score.getText();
        int newScore = Integer.parseInt(oldscore);
        score.setText(""+newScore);
    }
    private void showScore2(int inc){
        Log.i("show","inc="+inc);
        String oldscore = (String)score2.getText();
        int newScore = Integer.parseInt(oldscore);
        score2.setText(""+newScore);
    }
}