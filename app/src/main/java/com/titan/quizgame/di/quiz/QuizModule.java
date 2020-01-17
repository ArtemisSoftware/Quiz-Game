package com.titan.quizgame.di.quiz;

import android.app.Application;

import androidx.room.Room;

import com.titan.quizgame.database.MigrationDb;
import com.titan.quizgame.player.persistence.PlayerDao;
import com.titan.quizgame.quiz.persistence.CategoryDao;
import com.titan.quizgame.quiz.persistence.QuestionDao;
import com.titan.quizgame.database.QuizDatabase;
import com.titan.quizgame.quiz.repository.QuizRepository;
import com.titan.quizgame.util.constants.DataBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public class QuizModule {


    @Singleton
    @Provides
    static QuizDatabase provideQuizDatabase(Application application){

        QuizDatabase quizDatabase = Room.databaseBuilder(application, QuizDatabase.class, DataBase.DATABASE_NAME)
                .addMigrations(MigrationDb.MIGRATIONS)
                .build();

        Timber.d("Providing quiz database: " + quizDatabase);

        return quizDatabase;
    }


    @Singleton
    @Provides
    static QuestionDao provideQuestionDao(QuizDatabase quizDataBase){

        QuestionDao dao = quizDataBase.questionDao();

        Timber.d("Providing Question Dao: " + dao);

        return dao;
    }


    @Singleton
    @Provides
    static CategoryDao provideCategoryDao(QuizDatabase quizDataBase){

        CategoryDao dao = quizDataBase.categoryDao();

        Timber.d("Providing Category Dao: " + dao);

        return dao;
    }



    @Singleton
    @Provides
    static PlayerDao providePlayerDao(QuizDatabase quizDataBase){

        PlayerDao dao = quizDataBase.playerDao();

        Timber.d("Providing Player Dao: " + dao);

        return dao;
    }


    @Singleton
    @Provides
    static QuizRepository provideQuizRepository(QuestionDao questionDao, CategoryDao categoryDao, PlayerDao playerDao){

        QuizRepository repository = new QuizRepository(questionDao, categoryDao, playerDao);

        Timber.d("Providing Quiz Repository: " + repository);

        return repository;
    }


}
