package com.example.lihsh.assignment1;

public class Question {

    private int mTextId;
    private boolean mAnswerTrue;

    public Question(int TextId, boolean AnswerTrue){
        mTextId = TextId;
        mAnswerTrue = AnswerTrue;
    }

    public int getTextId(){
        return mTextId;
    }

    public void setTextId(int TextId){
        mTextId = TextId;
    }

    public boolean isAnswerTrue(){
        return mAnswerTrue;
    }

    public void setAnswerTrue(boolean answerTrue){
        mAnswerTrue = answerTrue;
    }
}
