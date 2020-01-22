package com.titan.quizgame.di;

import android.app.Application;

import com.titan.quizgame.App;
import com.titan.quizgame.di.player.PlayerModule;
import com.titan.quizgame.di.quiz.QuizModule;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;

@Singleton
@Component(
        modules = {
                AndroidInjectionModule.class,
                ActivityBuildersModule.class,
                AppModule.class,
                ViewModelFactoryModule.class,

                QuizModule.class,
                PlayerModule.class
        }
)
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    interface Builder{

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}