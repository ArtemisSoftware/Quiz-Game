package com.titan.quizgame.di.quiz;

import androidx.lifecycle.ViewModel;

import com.titan.quizgame.QuizViewModel;
import com.titan.quizgame.di.ViewModelKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class QuizViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(QuizViewModel.class)
    public abstract ViewModel bindQuizViewModel(QuizViewModel viewModel);
}
