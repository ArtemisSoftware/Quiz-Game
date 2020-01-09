package com.titan.quizgame.player.models;

import java.util.Date;

public class Board {

    private String name;

    private int place;
    private int highScore;
    //private String difficulty;
    private String category;

    private Date day;

    public Board(String name, int place, int highScore, String category, Date day) {
        this.name = name;
        this.place = place;
        this.highScore = highScore;
        this.category = category;
        this.day = day;
    }
}
