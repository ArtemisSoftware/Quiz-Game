package com.titan.quizgame.player.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "players",
        foreignKeys = @ForeignKey(entity = Player.class,
                                            parentColumns = "name",
                                            childColumns = "playerName",
                                            onDelete = ForeignKey.CASCADE)
)
public class Score {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "categoryId")
    private int categoryId;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "difficulty")
    private String difficulty;

    @NonNull
    @ColumnInfo(name = "playerName")
    private String playerName;


    @NonNull
    @ColumnInfo(name = "points")
    private int points;


    //private Date day;

    public Score(int points, int categoryId, String difficulty, String playerName) {
        this.points = points;
        this.categoryId = categoryId;
        this.difficulty = difficulty;
        this.playerName = playerName;
        //this.day = new Date();
    }



}
