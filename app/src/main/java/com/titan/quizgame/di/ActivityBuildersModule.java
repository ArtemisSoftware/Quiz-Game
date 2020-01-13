package com.titan.quizgame.di;

import com.titan.quizgame.MainActivity;
import com.titan.quizgame.di.quiz.QuizModule;
import com.titan.quizgame.di.quiz.QuizViewModelsModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    //@MainScope
    @ContributesAndroidInjector(
            modules = {QuizViewModelsModule.class, QuizModule.class}
    )
    abstract MainActivity contributeMainActivity();
}
