package com.example.lihsh.assignment1;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class congratulation_screen extends AppCompatActivity {
    SQlitehelper db = new SQlitehelper(this);
    String data_name;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_congratulation_screen);

        Bundle extras  = getIntent().getExtras();
        String data = extras.getString("KEY");
        data_name = extras.getString("username");

        TextView tv;

        tv = (TextView)findViewById(R.id.txt_message);

        tv.setText(data);

    }

    public void btn_return(View view) {
        Intent i = new Intent(congratulation_screen.this, MainMenu.class);
        i.putExtra("username", ""+data_name);
        startActivity(i);
    }
}
