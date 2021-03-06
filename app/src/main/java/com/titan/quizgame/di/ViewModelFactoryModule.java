package com.titan.quizgame.di;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.titan.quizgame.player.PlayerViewModel;
import com.titan.quizgame.quiz.QuizViewModel;
import com.titan.quizgame.util.viewmodel.ViewModelProviderFactory;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelFactoryModule {

    @Binds
    public abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelProviderFactory modelProviderFactory);


    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel.class)
    public abstract ViewModel bindQuizViewModel(QuizViewModel viewModel);


    @Binds
    @IntoMap
    @ViewModelKey(PlayerViewModel.class)
    public abstract ViewModel bindPlayerViewModel(PlayerViewModel viewModel);
}