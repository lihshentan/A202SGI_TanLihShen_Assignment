package com.example.lihsh.assignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainMenu extends AppCompatActivity {

    ListView listView;
    String data_name;
    SQlitehelper db = new SQlitehelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Bundle extras  = getIntent().getExtras();
        data_name = extras.getString("username");

        listView = (ListView)findViewById(R.id.listview);

        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("Country Quiz");
        arrayList.add("Math Question");
        arrayList.add("Science Quiz");
        arrayList.add("Challenge Mode");
        arrayList.add("Leaderboard");

        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,arrayList);

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(arrayList.get(position).toString() == "Country Quiz"){
                    Intent i = new Intent(MainMenu.this, country_quiz.class);
                    i.putExtra("username", ""+data_name);
                    startActivity(i);
                }
                if(arrayList.get(position).toString() == "Math Question"){
                    Intent i = new Intent(MainMenu.this,math_question.class);
                    i.putExtra("username", ""+data_name);
                    startActivity(i);
                }
                if(arrayList.get(position).toString() == "Science Quiz"){
                    Intent i = new Intent(MainMenu.this,science_quiz.class);
                    i.putExtra("username", ""+data_name);
                    startActivity(i);
                }
                if(arrayList.get(position).toString() == "Challenge Mode"){
                    Intent i = new Intent(MainMenu.this,challenge_mode.class);
                    i.putExtra("username", ""+data_name);
                    startActivity(i);
                }
                if(arrayList.get(position).toString() == "Leaderboard"){
                    Intent i = new Intent(MainMenu.this, leaderboard.class);
                    startActivity(i);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                Intent i = new Intent(MainMenu.this, profile.class);
                i.putExtra("username", ""+data_name);
                startActivity(i);
                return true;

            case R.id.logout:
                AlertDialog.Builder builder1 = new AlertDialog.Builder(MainMenu.this);
                builder1.setMessage("Are you sure you want to LOGOUT?");
                builder1.setCancelable(true);

                builder1.setPositiveButton(
                        "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

                builder1.setNegativeButton(
                        "Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Intent i = new Intent(MainMenu.this, MainActivity.class);
                                startActivity(i);
                            }
                        });

                AlertDialog alert11 = builder1.create();
                alert11.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
