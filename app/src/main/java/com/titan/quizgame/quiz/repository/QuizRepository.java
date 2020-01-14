package com.titan.quizgame.quiz.repository;

import android.app.Application;

import androidx.annotation.NonNull;

import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.persistence.CategoryDao;
import com.titan.quizgame.quiz.persistence.QuestionDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

@Singleton
public class QuizRepository {


    @NonNull
    private QuestionDao questionDao;

    //inject
    @NonNull
    private CategoryDao categoryDao;


    @Inject
    public QuizRepository(@NonNull QuestionDao questionDao, @NonNull CategoryDao categoryDao){
        this.questionDao = questionDao;
        this.categoryDao = categoryDao;
    }


    public Flowable<List<Question>> getQuestions(String difficulty, int categoryId) {
        return questionDao.getQuestions(difficulty, categoryId);
    }


    public Flowable<List<Category>> getCategories() {
        return categoryDao.getCategories();
    }


    /*
    @Insert
    Single<Long> insertPlayer(Player player) throws Exception;


    @Update
    Single<Integer> updatePlayer(Player player) throws Exception;
    */


}
