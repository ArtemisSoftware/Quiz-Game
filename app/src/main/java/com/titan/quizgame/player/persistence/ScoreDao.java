package com.titan.quizgame.player.persistence;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.titan.quizgame.player.models.Score;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface ScoreDao {

    @Insert
    Completable insert(Score score);

    @Query("SELECT points FROM score ORDER BY points DESC LIMIT 1")
    Maybe<Integer> getHighScore();
}
