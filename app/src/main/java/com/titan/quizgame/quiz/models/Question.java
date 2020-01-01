package com.titan.quizgame.quiz.models;

public class Question {

    private String question;
    private String option1;
    private String option2;
    private String option3;
    private int answerNr;

    public Question(String question, String option1, String option2, String option3, int answerNr) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNr = answerNr;
    }
}
