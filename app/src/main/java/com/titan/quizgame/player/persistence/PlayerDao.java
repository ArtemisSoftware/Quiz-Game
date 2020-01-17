package com.titan.quizgame.player.persistence;


import androidx.room.Dao;
import androidx.room.Insert;

import com.titan.quizgame.player.models.Player;


@Dao
public interface PlayerDao {

    @Insert
    void insert(Player player);
}