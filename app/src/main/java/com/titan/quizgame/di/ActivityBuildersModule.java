package com.titan.quizgame.di;

import com.titan.quizgame.MainActivity;
import com.titan.quizgame.di.quiz.QuizModule;
import com.titan.quizgame.di.quiz.QuizViewModelsModule;
import com.titan.quizgame.player.RegisterPlayerActivity;
import com.titan.quizgame.quiz.QuestionActivity;
import com.titan.quizgame.quiz.QuizActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityBuildersModule {


    //@MainScope
    @ContributesAndroidInjector(
            modules = {/*QuizViewModelsModule.class, QuizModule.class*/}
    )
    abstract MainActivity contributeMainActivity();


    //@MainScope
    @ContributesAndroidInjector(
            modules = {/*QuizViewModelsModule.class, QuizModule.class*/}
    )
    abstract QuestionActivity contributeQuestionActivity();



    //@MainScope
    @ContributesAndroidInjector(
            modules = {/*QuizViewModelsModule.class, QuizModule.class*/}
    )
    abstract QuizActivity contributeQuizActivityActivity();



    //@MainScope
    @ContributesAndroidInjector(
            modules = {/*PlayerViewModelsModule.class, PlayerModule.class*/}
    )
    abstract RegisterPlayerActivity contributeRegisterPlayerActivity();

}
