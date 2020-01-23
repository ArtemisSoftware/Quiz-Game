package com.titan.quizgame.player.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "score",
        foreignKeys = @ForeignKey(entity = Player.class,
                                            parentColumns = "name",
                                            childColumns = "playerName",
                                            onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = {"playerName"}, unique = true)}
)
public class Score {

    @PrimaryKey(autoGenerate = true)
    public int id;


    @NonNull
    @ColumnInfo(name = "categoryId")
    private int categoryId;

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

    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    @NonNull
    public String getDifficulty() {
        return difficulty;
    }

    @NonNull
    public String getPlayerName() {
        return playerName;
    }

    public int getPoints() {
        return points;
    }
}
