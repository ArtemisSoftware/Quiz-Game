package com.titan.quizgame.player.persistence;


import androidx.room.Dao;
import androidx.room.Insert;

import com.titan.quizgame.player.models.Player;

import io.reactivex.Completable;


@Dao
public interface PlayerDao {

    // Makes sure that the operation finishes successfully.
    @Insert
    Completable insert(Player player);
}