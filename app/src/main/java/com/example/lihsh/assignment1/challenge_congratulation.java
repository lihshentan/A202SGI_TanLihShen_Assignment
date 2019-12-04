package com.example.lihsh.assignment1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class challenge_congratulation extends AppCompatActivity {
    SQlitehelper db = new SQlitehelper(this);
    String data_name;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_congratulation);

        Bundle extras  = getIntent().getExtras();
        String data = extras.getString("KEY");
        data_name = extras.getString("username");
        String score = db.retrieve(data_name);

        TextView tv;

        tv = (TextView)findViewById(R.id.txt_message);
        textView = (TextView)findViewById(R.id.testing_score);
        tv.setText(data);
        textView.setText("Your current high-score in challenge mode is "+score);
    }

    public void btn_return(View view) {
        Intent i = new Intent(challenge_congratulation.this, MainMenu.class);
        i.putExtra("username", ""+data_name);
        startActivity(i);
    }

}
