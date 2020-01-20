package com.titan.quizgame.player.persistence;

import androidx.room.Dao;
import androidx.room.Insert;

import com.titan.quizgame.player.models.Score;

import io.reactivex.Completable;

@Dao
public interface ScoreDao {

    @Insert
    Completable insert(Score score);
}
