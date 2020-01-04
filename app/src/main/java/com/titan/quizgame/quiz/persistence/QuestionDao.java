package com.titan.quizgame.quiz.persistence;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.titan.quizgame.quiz.models.Question;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Dao
public interface QuestionDao {

    @Query("SELECT * from questions")
    public Flowable<List<Question>> getQuestions();

    // Emits the number of users added to the database.
    @Insert
    public Maybe<long[]> insertQuestions(List<Question> questions);

    // Makes sure that the operation finishes successfully.
    @Insert
    public Completable insertQuestions(Question... questions);

    /* Emits the number of users removed from the database. Always emits at
       least one user. */
    @Delete
    public Single<Integer> deleteQuestions(List<Question> users);
}
