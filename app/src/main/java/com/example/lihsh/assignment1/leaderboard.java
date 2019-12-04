package com.example.lihsh.assignment1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class leaderboard extends AppCompatActivity {

    ListView listView;
    List<String> allUser = new ArrayList<>();
    SQlitehelper db = new SQlitehelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leaderboard);
        listView = (ListView)findViewById(R.id.listview2);
        allUser = db.getAll();

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,allUser);

        listView.setAdapter(arrayAdapter);
    }
}
