package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class Constellations extends AppCompatActivity {
    TextView show;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constellations);

        show = (TextView)findViewById(R.id.flowers);


        Thread t = new Thread("this");
        t.start();




    }

    public void openOne(View btn){
        Log.i("open","openOne:");
        Intent config = new Intent(this,Constellations2.class);
        startActivityForResult(config,5);
    }

    public void openTwo(View btn){
        Log.i("open","openOne:");
        Intent config = new Intent(this,Constellation3.class);
        startActivityForResult(config,5);
    }

    public class DBHelper extends SQLiteOpenHelper {

        private static final int VERSION = 1;
        private static final String DB_NAME = "myflowers.db";
        public static final String TB_NAME = "tb_flowers";


        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }
        public DBHelper(Context context) {
            super(context,DB_NAME,null,VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE "+TB_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT,CURNAME TEXT,CURRATE TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
            // TODO Auto-generated method stub
        }
    }

    public static class FlowerItem {
        private int id;
        private String curName;

        public FlowerItem() {
            super();
            curName = "";

        }
        public FlowerItem(String curName) {
            super();
            this.curName = curName;
        }
        public int getId() {
            return id;
        }
        public void setId(int id) {
            this.id = id;
        }
        public String getCurName() {
            return curName;
        }
        public void setCurName(String curName) {
            this.curName = curName;
        }
    }
    public class DBManager {

        private DBHelper dbHelper;
        private String TBNAME;

        public DBManager(Context context) {
            dbHelper = new DBHelper(context);
            TBNAME = DBHelper.TB_NAME;
        }

        public void add(FlowerItem item){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("curname", item.getCurName());
            db.insert(TBNAME, null, values);
            db.close();
        }

        public void addAll(List<FlowerItem> list){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            for (FlowerItem item : list) {
                ContentValues values = new ContentValues();
                values.put("curname", item.getCurName());
                db.insert(TBNAME, null, values);
            }
            db.close();
        }

        public void deleteAll(){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TBNAME,null,null);
            db.close();
        }

        public void delete(int id){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            db.delete(TBNAME, "ID=?", new String[]{String.valueOf(id)});
            db.close();
        }

        public void update(FlowerItem item){
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("curname", item.getCurName());
            db.update(TBNAME, values, "ID=?", new String[]{String.valueOf(item.getId())});
            db.close();
        }

        public List<FlowerItem> listAll(){
            List<FlowerItem> FlowerList = null;
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TBNAME, null, null, null, null, null, null);
            if(cursor!=null){
                FlowerList = new ArrayList<FlowerItem>();
                while(cursor.moveToNext()){
                    FlowerItem item = new FlowerItem();
                    item.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                    item.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));

                    FlowerList.add(item);
                }
                cursor.close();
            }
            db.close();
            return FlowerList;

        }
        public FlowerItem findById(int id){
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query(TBNAME, null, "ID=?", new String[]{String.valueOf(id)}, null, null, null);
            FlowerItem flowerItem = null;
            if(cursor!=null && cursor.moveToFirst()){
                flowerItem = new FlowerItem();
                flowerItem.setId(cursor.getInt(cursor.getColumnIndex("ID")));
                flowerItem.setCurName(cursor.getString(cursor.getColumnIndex("CURNAME")));
                cursor.close();
            }
            db.close();
            return flowerItem;
        }
    }


}