package com.titan.quizgame.player.repository;

import androidx.annotation.NonNull;

import com.titan.quizgame.player.models.Player;
import com.titan.quizgame.player.models.Score;
import com.titan.quizgame.player.persistence.PlayerDao;
import com.titan.quizgame.player.persistence.ScoreDao;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;

@Singleton
public class PlayerRepository {


    @NonNull
    private PlayerDao playerDao;

    @NonNull
    private ScoreDao scoreDao;

    @Inject
    public PlayerRepository(@NonNull PlayerDao playerDao, @NonNull ScoreDao scoreDao){
        this.playerDao = playerDao;
        this.scoreDao = scoreDao;
    }


    public Completable savePlayer(Player player) {
        return playerDao.insert(player);
    }


    public Completable saveScore(Score score) {
        return scoreDao.insert(score);
    }


/*
    public Flowable<List<Question>> getQuestions(String difficulty, int categoryId) {
        return questionDao.getQuestions(difficulty, categoryId);
    }
*/
}
