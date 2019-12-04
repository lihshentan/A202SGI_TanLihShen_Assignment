package com.example.lihsh.assignment1;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class challenge_mode extends AppCompatActivity {

    String data_name;
    SQlitehelper db = new SQlitehelper(this);

    Button btn_true;
    Button btn_false;
    Button btn_giveUp;

    TextView timer;
    TextView tv_question;

    Question[] questionBank = new Question[]{
            new Question(R.string.question_malaysia, true),
            new Question(R.string.question_math1, true),
            new Question(R.string.question_science1, true),
            new Question(R.string.qustion_russia,true),
            new Question(R.string.question_math2,false),
            new Question(R.string.question_science2,true),
            new Question(R.string.question_math3,true),
            new Question(R.string.question_science3,true),
            new Question(R.string.question_china,true),
            new Question(R.string.challenge1,false),
            new Question(R.string.challenge2, false),
            new Question(R.string.challenge3, false),
            new Question(R.string.challenge4, true),
            new Question(R.string.challenge5, true),
            new Question(R.string.challenge6, false)
    };

    int currentIndex = 0;
    int correct = 0;
    int wrong = 0;
    boolean endNow = false;


    private void updateQuestion(){
        String newScore = String.valueOf(correct);
        String oldScore = db.retrieve(data_name);
        if(correct + wrong == 15) {
            if(Integer.parseInt(oldScore) < correct) {
                db.updateScore(data_name, newScore);
                Intent i = new Intent(challenge_mode.this, challenge_congratulation.class);
                i.putExtra("KEY", "Correct answers: " + correct + "\n Wrong Answers: " + wrong);
                i.putExtra("username", "" + data_name);
                endNow = true;
                startActivity(i);
                finish();
            }else {
                Intent i = new Intent(challenge_mode.this, challenge_congratulation.class);
                i.putExtra("KEY", "Correct answers: " + correct + "\n Wrong Answers: " + wrong);
                i.putExtra("username", "" + data_name);
                endNow = true;
                startActivity(i);
                finish();
            }
        }else {
            int question = questionBank[currentIndex].getTextId();
            tv_question.setText(question);
        }
    }

    private void checkAnswer(boolean pressedTrue){
        boolean isTrue = questionBank[currentIndex].isAnswerTrue();

        if(pressedTrue == isTrue){
            Toast.makeText(challenge_mode.this,"Correct answer!",Toast.LENGTH_LONG).show();
            correct++;

        }else{
            Toast.makeText(challenge_mode.this,"Wrong answer!",Toast.LENGTH_LONG).show();
            wrong++;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_mode);
        endNow = false;
        Bundle extras  = getIntent().getExtras();
        data_name = extras.getString("username");

        timer = (TextView)findViewById(R.id.timer);

        new CountDownTimer(10000, 1000) {

            public void onTick(long millisUntilFinished) {
                timer.setText("seconds remaining: " + millisUntilFinished / 1000);

            }
            public void onFinish() {
                if(endNow == true){
                    this.cancel();
                }else {
                    String newScore = String.valueOf(correct);
                    String oldScore = db.retrieve(data_name);
                    if (Integer.parseInt(oldScore) < correct) {
                        db.updateScore(data_name, newScore);
                        Intent i = new Intent(challenge_mode.this, challenge_congratulation.class);
                        i.putExtra("KEY", "Correct answers: " + correct + "\n Wrong Answers: " + wrong);
                        i.putExtra("username", "" + data_name);

                        startActivity(i);
                        finish();
                    } else {
                        Intent i = new Intent(challenge_mode.this, challenge_congratulation.class);
                        i.putExtra("KEY", "Correct answers: " + correct + "\n Wrong Answers: " + wrong);
                        i.putExtra("username", "" + data_name);

                        startActivity(i);
                        finish();
                    }
                }
            }
        }.start();

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

                AlertDialog.Builder builder1 = new AlertDialog.Builder(challenge_mode.this);
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
                                endNow = true;
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
