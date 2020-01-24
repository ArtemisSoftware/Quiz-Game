package com.titan.quizgame.di.quiz;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.titan.quizgame.database.MigrationDb;
import com.titan.quizgame.database.PopulateDb;
import com.titan.quizgame.player.persistence.PlayerDao;
import com.titan.quizgame.player.persistence.ScoreDao;
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
                //.addCallback(dbCallback)
                .build();

        Timber.d("Providing quiz database: " + quizDatabase);

        return quizDatabase;
    }

/*
    static RoomDatabase.Callback provideRoomDatabaseCallBack(QuizDatabase quizDatabase){

        RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
                new PopulateDb(quizDatabase).execute();
            }
        };

        Timber.d("Providing quiz database: " + quizDatabase);

        return roomCallback;
    }
*/

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
    static ScoreDao provideScoreDao(QuizDatabase quizDataBase){

        ScoreDao dao = quizDataBase.scoreDao();

        Timber.d("Providing Score Dao: " + dao);

        return dao;
    }


    @Singleton
    @Provides
    static QuizRepository provideQuizRepository(QuestionDao questionDao, CategoryDao categoryDao, PlayerDao playerDao, ScoreDao scoreDao){

        QuizRepository repository = new QuizRepository(questionDao, categoryDao, playerDao, scoreDao);

        Timber.d("Providing Quiz Repository: " + repository);

        return repository;
    }


}
