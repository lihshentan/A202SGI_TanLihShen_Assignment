package com.example.lihsh.assignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class profile extends AppCompatActivity {

    TextView et_name, tv_score;
    Button btn, btn2;
    SQlitehelper db = new SQlitehelper(this);
    EditText input;
    String data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        et_name = (TextView)findViewById(R.id.tv_name);
        btn = (Button)findViewById(R.id.btn_delete);
        btn2 = (Button)findViewById(R.id.btn_update);
        input = (EditText)findViewById(R.id.txt_update);
        tv_score = (TextView)findViewById(R.id.et_score);

        Bundle extras  = getIntent().getExtras();
        final String data_name = extras.getString("username");

        String score = db.retrieve(data_name);

        et_name.setText("(" + data_name +")");
        tv_score.setText("Your current high score in challenge mode is "+score);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder1 = new AlertDialog.Builder(profile.this);
                builder1.setMessage("Are you sure you want to DELETE YOUR ACCOUNT?");
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

                                Boolean validate = db.delete(data_name);
                                if(validate == true){
                                    Toast.makeText(profile.this,"Deletion of user [" + data_name+ "] unsuccessful ",Toast.LENGTH_LONG).show();

                                }else{
                                    Toast.makeText(profile.this,"Deletion of user ["+data_name+ "] successful",Toast.LENGTH_LONG).show();
                                    Intent i = new Intent(profile.this, MainActivity.class);
                                    startActivity(i);
                                }
                            }
                        });
                AlertDialog alert11 = builder1.create();
                alert11.show();
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                data = input.getText().toString();

                if(data.equals("")){
                    Toast.makeText(profile.this,"ERROR: Fields are empty! ["+data+ "]",Toast.LENGTH_LONG).show();
                }else{
                    Boolean doesntExists = db.checkUserName(data);
                    if(doesntExists == true){
                       db.update(data_name,data);
                        Toast.makeText(profile.this,"Successfully update name!",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(profile.this, MainActivity.class);
                        startActivity(i);
                    }else{
                        Toast.makeText(profile.this,"ERROR: Username already exists!",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

    }
}
