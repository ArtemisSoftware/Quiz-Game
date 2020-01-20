package com.titan.quizgame.player.models;

public class Board {

    private String name;

    //private int place;
    private int points;
    private String difficulty;
    private String category;

    //private Date day;

    public Board(String name/*, int place*/, int points, String category, String difficulty/*, Date day*/) {
        this.name = name;
        //this.place = place;
        this.points = points;
        this.category = category;
        this.difficulty = difficulty;
        //this.day = day;
    }


    public String getName() {
        return name;
    }
/*
    public int getPlace() {
        return place;
    }
*/
    public int getPoints() {
        return points;
    }

    public String getCategory() {
        return category;
    }

    public String getDifficulty() {
        return difficulty;
    }

    /*
    public Date getDay() {
        return day;
    }
    */

}
