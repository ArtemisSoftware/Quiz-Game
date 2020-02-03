package com.titan.quizgame.player.repository;

import androidx.annotation.NonNull;

import com.titan.quizgame.network.ImageResponse;
import com.titan.quizgame.network.ImgurApi;
import com.titan.quizgame.player.models.Board;
import com.titan.quizgame.player.models.Player;
import com.titan.quizgame.player.models.Score;
import com.titan.quizgame.player.persistence.PlayerDao;
import com.titan.quizgame.player.persistence.ScoreDao;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;

@Singleton
public class PlayerRepository {


    @NonNull
    private PlayerDao playerDao;

    @NonNull
    private ScoreDao scoreDao;


    private ImgurApi imgurApi;

    @Inject
    public PlayerRepository(@NonNull PlayerDao playerDao, @NonNull ScoreDao scoreDao, @NonNull ImgurApi imgurApi){
        this.playerDao = playerDao;
        this.scoreDao = scoreDao;
        this.imgurApi = imgurApi;
    }


    public Completable savePlayer(Player player) {
        return playerDao.insert(player);
    }


    public Completable saveScore(Score score) {
        return scoreDao.insert(score);
    }


    public Single<Integer> playerExists(String name) {
        return playerDao.playerCount(name);
    }

    public Observable<List<Board>> getLeaderBoard() {
        return playerDao.getLeaderBoard();
    }


    public Observable<ImageResponse> postPlayerImage(String name, String description, String albumId, String username, MultipartBody.Part file) {
        return imgurApi.postImage(name, description, albumId, username, file);
    }

}
