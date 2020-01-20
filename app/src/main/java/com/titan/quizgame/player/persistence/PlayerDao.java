package com.titan.quizgame.player.persistence;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.titan.quizgame.player.models.Player;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;


@Dao
public interface PlayerDao {

    // Makes sure that the operation finishes successfully.
    @Insert
    Completable insert(Player player);


    @Query("SELECT COUNT(*) from players WHERE name  = :name")
    Single<Integer> playerCount(String name);


    @Query("SELECT * from players")
    Observable<List<Player>> getLeaderBoard();

}