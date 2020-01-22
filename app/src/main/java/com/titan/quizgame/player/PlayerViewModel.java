package com.titan.quizgame.player;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.quizgame.player.models.Board;
import com.titan.quizgame.player.models.Player;
import com.titan.quizgame.player.models.Score;
import com.titan.quizgame.player.repository.PlayerRepository;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.ui.Resource;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class PlayerViewModel extends ViewModel {

    private final CompositeDisposable disposables;
    private final PlayerRepository playerRepository;


    private MutableLiveData<Resource> playersLiveData;

    //@Inject
    public PlayerViewModel(PlayerRepository playerRepository) {

        this.playerRepository = playerRepository;
        this.disposables = new CompositeDisposable();

        playersLiveData = new MutableLiveData<>();

        Timber.d("Player repository: " + this.playerRepository);
        Timber.d("PlayerViewModel is ready");
    }

    public MutableLiveData<Resource> observePlayers(){
        return playersLiveData;
    }




    public void saveScore(Player player, Score score) {

        playerRepository.playerExists(player.getName())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer response) throws Exception {

                        return response; // B.
                    }
                })
                .flatMapCompletable(new Function<Integer, Completable>() {
                    @Override
                    public Completable apply(Integer response) throws Exception {

                        Completable action;

                        if (response == 0) { //nao existe
                            action = Completable.concatArray(playerRepository.savePlayer(player), playerRepository.saveScore(score));
                        }
                        else {
                            action = Completable.concatArray(playerRepository.saveScore(score));
                        }

                        return action;
                    }
                })
                .doOnSubscribe(__ -> {
                    //Log.w(LOG_TAG, "Begin transaction. " + Thread.currentThread().toString());
                    //mRoomDatabase.beginTransaction();
                })
                .doOnComplete(() -> {
                    //Log.w(LOG_TAG, "Set transaction successful."  + Thread.currentThread().toString());
                    //mRoomDatabase.setTransactionSuccessful();
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Timber.d("End transaction." + Thread.currentThread().toString());
                        //mRoomDatabase.endTransaction();
                    }
                })
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread()) // ON UI THREAD
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        if (d != null) {
                            disposables.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete." + Thread.currentThread().toString());
                        playersLiveData.setValue(Resource.success(null, "Score saved"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(LOG_TAG, "onError." + Thread.currentThread().toString());
                    }
                });

    }



    public void loadLeaderBoard() {

        disposables.add(
                //getting flowable to subscribe consumer that will access the data from Room database.
                playerRepository.getLeaderBoard()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<Board>>() {
                                    @Override
                                    public void accept(List<Board> questions) throws Exception {

                                        playersLiveData.setValue(Resource.success(questions, ""));

                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {

                                    }
                                }
                        )
        );
    }

}
