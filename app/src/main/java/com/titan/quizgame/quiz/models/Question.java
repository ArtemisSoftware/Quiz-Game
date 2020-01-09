package com.titan.quizgame.quiz.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions")
public class Question {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "question")
    private String question;

    @NonNull
    @ColumnInfo(name = "option1")
    private String option1;

    @NonNull
    @ColumnInfo(name = "option2")
    private String option2;

    @NonNull
    @ColumnInfo(name = "option3")
    private String option3;

    @NonNull
    @ColumnInfo(name = "answer")
    private int answerNr;

    public Question(String question, String option1, String option2, String option3, int answerNr) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNr = answerNr;
    }

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public int getAnswerNr() {
        return answerNr;
    }
}
