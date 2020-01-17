package com.titan.quizgame.player.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Player {


    @PrimaryKey
    private String name;

    public Player(String name) {
        this.name = name;
    }


}
