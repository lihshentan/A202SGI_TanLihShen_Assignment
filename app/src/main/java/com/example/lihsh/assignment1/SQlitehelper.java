package com.example.lihsh.assignment1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SQlitehelper extends SQLiteOpenHelper {

    public SQlitehelper(Context context){
        super(context,"QuizApp.db",null,3);
    }


    //added a new score attribute
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table user(username text primary key, password text, score text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists user");
    }


    //added new score attribute
    public boolean insert(String username, String password, String score){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password",password);
        contentValues.put("score",score);
        long ins = db.insert("user", null, contentValues);
        if(ins == 1){
            return false;
        }else{
            return true;
        }
    }

    //check for username
    public Boolean checkUserName(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=?", new String[]{username});
        if(cursor.getCount()>0){
            return false;
        }else{
            return true;
        }
    }

    //delete function
    public Boolean delete(String username){
        SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery("delete from user where username=?", new String[]{username});
       if(cursor.getCount()>0){
            return true;
       }else{
           return false;
      }
    }

    //update username function
    public void update(String oldUsername, String newUsername){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("username",newUsername);
        db.update("user", cv,"username "+ "=?",new String[]{oldUsername});
    }

    public void updateScore(String username, String newScore){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("score",newScore);
        db.update("user", cv,"username "+ "=?",new String[]{username});
    }

    //testing only
    public String retrieve(String username){
        String value="test";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select score from user where username=?", new String[]{username});
        if(cursor.moveToFirst()){
            do {
                value = cursor.getString(cursor.getColumnIndex("score"));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return value;
    }

    //check for username and password
    public boolean validateUser(String username, String password){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from user where username=? and password=?",new String[]{username,password});
        if(cursor.getCount() > 0){
            return true;
        }else{

            return false;
        }
    }

    public List<String> getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from " + "user", null);
        List<String> fileName = new ArrayList<>();

        if (cursor.moveToFirst()){
            fileName.add("User score: "+(cursor.getString(cursor.getColumnIndex("score"))) + "\nUsername: " + (cursor.getString(cursor.getColumnIndex("username"))) );

            while(cursor.moveToNext()){
                fileName.add("User score: "+(cursor.getString(cursor.getColumnIndex("score"))) + "\nUsername: " + (cursor.getString(cursor.getColumnIndex("username"))) );
            }
        }
        cursor.close();
        db.close();

        Collections.sort(fileName, new Comparator<String>() {
            public int compare(String o1, String o2) {
                return extractInt(o1) - extractInt(o2);
            }

            int extractInt(String s) {
                String num = s.replaceAll("\\D", "");
                // return 0 if no digits found
                return num.isEmpty() ? 0 : Integer.parseInt(num);
            }
        });

        List<String> newList = new ArrayList<String>();
        int size = fileName.size()-1;

        for(int i=size;i>=0;i--){
            newList.add(fileName.get(i));
        }

        return(newList);
    }
}
