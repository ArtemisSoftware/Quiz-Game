package com.titan.quizgame.player;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.quizgame.player.repository.PlayerRepository;
import com.titan.quizgame.ui.Resource;

import io.reactivex.disposables.CompositeDisposable;
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
}
