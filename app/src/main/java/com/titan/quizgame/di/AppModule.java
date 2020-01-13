package com.titan.quizgame.di;

import android.app.Application;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.titan.quizgame.R;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import timber.log.Timber;

@Module
public class AppModule {


    @Singleton
    @Provides
    static RequestOptions provideRequestOptions(){

        RequestOptions requestOptions = RequestOptions
                                            .placeholderOf(R.drawable.white_background)
                                            .error(R.drawable.white_background);

        Timber.d("Providing RequestOptions: " + requestOptions);

        return requestOptions;
    }

    @Singleton
    @Provides
    static RequestManager provideGlideInstance(Application application, RequestOptions requestOptions){

        RequestManager requestManager = Glide.with(application)
                                            .setDefaultRequestOptions(requestOptions);

        Timber.d("Providing RequestManager: " + requestManager);

        return requestManager;
    }

    @Singleton
    @Provides
    static Drawable provideAppBanner(Application application){
        Drawable banner = ContextCompat.getDrawable(application, R.drawable.quiz_logo);

        Timber.d("Providing App Banner: " + banner);

        return banner;
    }

}
