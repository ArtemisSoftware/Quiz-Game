package com.titan.quizgame.quiz.repository;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.titan.quizgame.player.models.Score;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.persistence.CategoryDao;
import com.titan.quizgame.quiz.persistence.QuestionDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
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


    public Completable saveQuestion(Question question) {
        return questionDao.insertQuestions(question);
    }


    public Completable saveScore(Score score) {
        return null;//scoreDao.insert(score);
    }






    /*
    public void insert(Category note) {
        new InsertNoteAsyncTask(categoryDao).execute(note);
    }

    private static class InsertNoteAsyncTask extends AsyncTask<Category, Void, Void> {
        private CategoryDao noteDao;

        private InsertNoteAsyncTask(CategoryDao noteDao) {
            this.noteDao = noteDao;
        }

        @Override
        protected Void doInBackground(Category... notes) {
            noteDao.insert(notes[0]);
            return null;
        }
    }
*/

    /*
    @Insert
    Single<Long> insertPlayer(Player player) throws Exception;


    @Update
    Single<Integer> updatePlayer(Player player) throws Exception;
    */


}
