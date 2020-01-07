package com.titan.quizgame.quiz.persistence;

import androidx.room.Dao;
import androidx.room.Insert;

import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;

import java.util.List;

import io.reactivex.Maybe;

@Dao
public interface CategoryDao {

    // Emits the number of users added to the database.
    @Insert
    public Maybe<long[]> insertCategories(List<Category> categories);
}
