package com.titan.quizgame.di.player;

import android.app.Application;

import androidx.room.Room;

import com.titan.quizgame.database.MigrationDb;
import com.titan.quizgame.database.QuizDatabase;
import com.titan.quizgame.network.ImgurApi;
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


    @Singleton
    @Provides
    static PlayerRepository providePlayerRepository(PlayerDao playerDao, ScoreDao scoreDao, ImgurApi imgurApi){

        PlayerRepository repository = new PlayerRepository(playerDao, scoreDao, imgurApi);

        Timber.d("Providing Player Repository: " + repository);

        return repository;
    }

}
