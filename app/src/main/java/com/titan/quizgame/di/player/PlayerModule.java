package com.titan.quizgame.di.player;

import android.app.Application;

import androidx.room.Room;

import com.titan.quizgame.database.MigrationDb;
import com.titan.quizgame.database.QuizDatabase;
import com.titan.quizgame.player.persistence.PlayerDao;
import com.titan.quizgame.player.persistence.ScoreDao;
import com.titan.quizgame.player.repository.PlayerRepository;
import com.titan.quizgame.util.constants.DataBase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public class PlayerModule {

/*
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
    static PlayerDao providePlayerDao(QuizDatabase quizDataBase){

        PlayerDao dao = quizDataBase.playerDao();

        Timber.d("Providing Player Dao: " + dao);

        return dao;
    }
*/
/*
    @Singleton
    @Provides
    static ScoreDao provideScoreDao(QuizDatabase quizDataBase){

        ScoreDao dao = quizDataBase.scoreDao();

        Timber.d("Providing Score Dao: " + dao);

        return dao;
    }
*/

    @Singleton
    @Provides
    static PlayerRepository providePlayerRepository(PlayerDao playerDao, ScoreDao scoreDao){

        PlayerRepository repository = new PlayerRepository(playerDao, scoreDao);

        Timber.d("Providing Player Repository: " + repository);

        return repository;
    }

}
