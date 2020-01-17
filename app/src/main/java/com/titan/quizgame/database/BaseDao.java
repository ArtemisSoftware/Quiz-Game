package com.titan.quizgame.database;

import androidx.room.Insert;

public interface BaseDao<T> {

    @Insert
    void insert(T register);
}
