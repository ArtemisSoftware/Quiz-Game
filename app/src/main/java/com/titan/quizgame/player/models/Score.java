package com.titan.quizgame.player.models;

import java.util.Date;

public class Score {

    private int id;
    private int points;
    private int categoryId;
    private String difficulty;
    private int playerId;
    private Date day;

    public Score(int points, int categoryId, String difficulty, int playerId) {
        this.points = points;
        this.categoryId = categoryId;
        this.difficulty = difficulty;
        this.playerId = playerId;
        this.day = new Date();
    }
}
