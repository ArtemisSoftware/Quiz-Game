package com.titan.quizgame.quiz.repository;

import android.app.Application;

import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.persistence.CategoryDao;
import com.titan.quizgame.quiz.persistence.QuestionDao;
import com.titan.quizgame.quiz.persistence.QuizDatabase;

import java.util.List;

import io.reactivex.Flowable;

public class QuizRepository {


    private QuestionDao questionDao;
    private CategoryDao categoryDao;

    public QuizRepository(Application application) {
        QuizDatabase database = QuizDatabase.getInstance(application);
        questionDao = database.questionDao();
        categoryDao = database.categoryDao();

    }

    public Flowable<List<Question>> getQuestions(String difficulty, int categoryId) {
        return questionDao.getQuestions(difficulty, categoryId);
    }


    public Flowable<List<Category>> getCategories() {
        return categoryDao.getCategories();
    }

}
