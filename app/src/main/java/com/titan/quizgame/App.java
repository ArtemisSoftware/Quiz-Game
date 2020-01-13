package com.titan.quizgame;

import com.titan.quizgame.di.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import timber.log.Timber;

public class App extends DaggerApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        Timber.plant(new Timber.DebugTree());
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {

        Timber.d("AndroidInjector... ");

        return DaggerAppComponent.builder().application(this).build();
    }
}
