package com.titan.quizgame.di.player;

import androidx.lifecycle.ViewModel;

import com.titan.quizgame.di.ViewModelKey;
import com.titan.quizgame.player.PlayerViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class PlayerViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel.class)
    public abstract ViewModel bindQuizViewModel(PlayerViewModel viewModel);
}
