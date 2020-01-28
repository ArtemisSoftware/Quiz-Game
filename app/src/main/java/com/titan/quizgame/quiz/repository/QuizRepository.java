package com.titan.quizgame.quiz.repository;

import android.os.AsyncTask;

import androidx.annotation.NonNull;

import com.titan.quizgame.player.models.Player;
import com.titan.quizgame.player.models.Score;
import com.titan.quizgame.player.persistence.PlayerDao;
import com.titan.quizgame.player.persistence.ScoreDao;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.persistence.CategoryDao;
import com.titan.quizgame.quiz.persistence.QuestionDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

@Singleton
public class QuizRepository {


    @NonNull
    private QuestionDao questionDao;

    //inject
    @NonNull
    private CategoryDao categoryDao;

    @NonNull
    private PlayerDao playerDao;

    @NonNull
    private ScoreDao scoreDao;

    @Inject
    public QuizRepository(@NonNull QuestionDao questionDao, @NonNull CategoryDao categoryDao, @NonNull PlayerDao playerDao, @NonNull ScoreDao scoreDao){
        this.questionDao = questionDao;
        this.categoryDao = categoryDao;
        this.playerDao = playerDao;
        this.scoreDao = scoreDao;
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


    public Single<Integer> playerExists(String name) {
        return playerDao.playerCount(name);
    }


    public Completable savePlayer(Player player) {
        return playerDao.insert(player);
    }


    public Completable saveScore(Score score) {
        return scoreDao.insert(score);
    }

    public Maybe<Integer> getHighScore() {
        return scoreDao.getHighScore();
    }






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


}
