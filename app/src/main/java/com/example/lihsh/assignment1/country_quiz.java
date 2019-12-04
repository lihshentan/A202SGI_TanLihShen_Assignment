package com.example.lihsh.assignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class country_quiz extends AppCompatActivity {


    Button btn_true;
    Button btn_false;
    Button btn_giveUp;
    String data_name;
    SQlitehelper db = new SQlitehelper(this);

    TextView tv_question;

    Question[] questionBank = new Question[]{
            new Question(R.string.question_malaysia, true),
            new Question(R.string.qustion_russia,true),
            new Question(R.string.question_china,true),
    };

    int currentIndex = 0;
    int correct = 0;
    int wrong = 0;

    private void updateQuestion(){
        if(correct + wrong == 3) {
                Intent i = new Intent(country_quiz.this, congratulation_screen.class);
                i.putExtra("KEY", "Correct answers: " + correct + "\n Wrong Answers: " + wrong);
                i.putExtra("username", "" + data_name);
                startActivity(i);
        }else {
            int question = questionBank[currentIndex].getTextId();
            tv_question.setText(question);
        }
    }

    private void checkAnswer(boolean pressedTrue){
        boolean isTrue = questionBank[currentIndex].isAnswerTrue();

        if(pressedTrue == isTrue){
            Toast.makeText(country_quiz.this,"Correct answer!",Toast.LENGTH_LONG).show();
            correct++;

        }else{
            Toast.makeText(country_quiz.this,"Wrong answer!",Toast.LENGTH_LONG).show();
            wrong++;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_quiz);

        Bundle extras  = getIntent().getExtras();
        data_name = extras.getString("username");

        tv_question = (TextView)findViewById(R.id.question);

        btn_true = (Button)findViewById(R.id.btn_true);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(true);
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        btn_false = (Button)findViewById(R.id.btn_false);
        btn_false.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(false);
                currentIndex = (currentIndex + 1) % questionBank.length;
                updateQuestion();
            }
        });

        btn_giveUp = (Button)findViewById(R.id.btn_giveUp);
        btn_giveUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    AlertDialog.Builder builder1 = new AlertDialog.Builder(country_quiz.this);
                    builder1.setMessage("Are you sure you want to give up?");
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
                                    finish();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
            }
        });
        updateQuestion();
    }
}
