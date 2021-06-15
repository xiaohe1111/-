 package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity4 extends AppCompatActivity implements Runnable {

    EditText rmb;
    TextView show;
    float dollarRate = 0.15f;
    float euroRate = 0.12f;
    float wonRate = 172.0f;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        rmb = (EditText)findViewById(R.id.rmb);
        show = (TextView)findViewById(R.id.show);

        SharedPreferences sp = getSharedPreferences("myrate",MainActivity4.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        dollarRate = sp.getFloat("dollar_rate",0.0f);
        euroRate = sp.getFloat("euro_rate",0.0f);
        wonRate = sp.getFloat("won_rate",0.0f);

        editor.putFloat("dollar_rate",dollarRate);
        editor.putFloat("euro_rate",euroRate);
        editor.putFloat("won_rate",wonRate);
        editor.apply();

        Thread t = new Thread("this");
        t.start();

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 7) {
                    String str = (String) msg.obj;
                    Log.i("TAG", "handleMessage:getMessage msg=" + str);
                    show.setText(str);
                }
                super.handleMessage(msg);
            }
        };
    }
    public void onClick(View btn){
        Log.d("TAG",  "click:");
        float r = 0.0f;
        switch (btn.getId()){
            case R.id.dollar:
                r = dollarRate;
            case R.id.euro:
                r = euroRate;
            case R.id.wcn:
                r = wonRate;
        }
        String str = rmb.getText().toString();
        Log.i("TAG",  "click:str="+str);
        if(str==null||str.length()==0){
            Toast.makeText(this,"请输入金额",Toast.LENGTH_SHORT).show();
        }else{
            r = r*Float.parseFloat(str);
            show.setText(String.valueOf(r));
        }
    }
    public void openOne(View btn){
        Log.i("open","openOne:");
        Intent config = new Intent(this,MainActivity5.class);
        config.putExtra("dollar_rate_key",dollarRate);
        config.putExtra("euro_rate_key",euroRate);
        config.putExtra("won_rate_key",wonRate);

        Log.i("TAG","openConfig:dollarRate="+dollarRate);
        Log.i("TAG","openConfig:euroRate="+euroRate);
        Log.i("TAG","openConfig:wonRate="+wonRate);

        startActivityForResult(config,5);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.rate,menu);
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        if(requestCode==5&&resultCode==2){
            Bundle bundle = data.getExtras();
            dollarRate = bundle.getFloat("key_dollar",0.1f);
            euroRate = bundle.getFloat("key_euro",0.1f);
            wonRate = bundle.getFloat("key_won",0.1f);

            Log.i("TAG","onActivityResult:dollarRate="+dollarRate);
            Log.i("TAG","onActivityResult:euroRate="+euroRate);
            Log.i("TAG","onActivityResult:wonRate="+wonRate);
        }
        super.onActivityResult(requestCode,resultCode,data);
    }
    public void run(){
        Log.i("TAG","run():............");
        try{
            Thread.sleep(3000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        Message msg = handler.obtainMessage(7);
        msg.obj = "Hello from run()";
        handler.sendMessage(msg);

        URL url = null;
        try {
            //url = new URL("http://www.usd-cny.com/bankofchina.htm");
            //HttpURLConnection http = (HttpURLConnection)url.openConnection();
            //InputStream in = http.getInputStream();

            //String html = inputStream2String(in);
            //Log.i("TAG","run:html="+html);
            Document doc = Jsoup.connect("http://www.usd-cny.com/bankofchina.htm").get();
            Log.i("TAG","run:title="+doc.title());

            Element publicTime = doc.getElementsByClass("time").first();
            Log.i("TAG","run:time="+publicTime.html());
            Element table = doc.getElementsByTag("table").first();
            Elements trs = table.getElementsByTag("tr");

            for(Element tr:trs){
                Elements tds = table.getElementsByTag("td");
                if(tds.size()>0){
                    Log.i("TAG","run:td="+tds.first().text());
                    Log.i("TAG","rate="+tds.get(5).text());
                }
            }
        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        }
    private String inputStream2String(InputStream inputStream)throws IOException{
            final int bufferSize = 1024;
            final char[] buffer = new char[bufferSize];
            final StringBuffer out = new StringBuffer();
            Reader in = new InputStreamReader(inputStream,"gb2312");
            while (true){
                int rsz = in.read(buffer,0,buffer.length);
                if(rsz < 0)
                    break;
                out.append(buffer,0,rsz);
            }
            return out.toString();
        }
    public String changeCharset(String str, String newCharset)
            throws UnsupportedEncodingException {
        if (str != null) {
            byte[] bs = str.getBytes();
            return new String(bs, newCharset);
        }
        return null;
    }
    public void AutoCode(){
        try{
            String beforeUrl = "http://www.usd-cny.com/bankofchina.htm";
            URL url2 = new URL(beforeUrl);
            HttpURLConnection conn2= (HttpURLConnection) url2.openConnection();
            InputStreamReader in2 = new InputStreamReader(conn2.getInputStream());
            BufferedReader read2 = new BufferedReader(in2);
            String s2;
            StringBuffer resultBuffer2 = new StringBuffer();
            while((s2 = read2.readLine()) != null) {
                resultBuffer2.append(s2);
                resultBuffer2.append("\n");
            }
            in2.close();
            s2 = resultBuffer2.toString();
            System.out.println(s2);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
