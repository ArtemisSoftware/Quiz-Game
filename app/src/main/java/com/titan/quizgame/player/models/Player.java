package com.titan.quizgame.player.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "players")
public class Player {


    @PrimaryKey
    @ColumnInfo(name = "name")
    private String name;

    public Player(String name) {
        this.name = name;
    }


}
