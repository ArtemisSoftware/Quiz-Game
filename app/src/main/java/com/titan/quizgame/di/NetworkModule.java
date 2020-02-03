package com.titan.quizgame.di;

import com.titan.quizgame.network.ApiInterceptor;
import com.titan.quizgame.network.ImgurApi;
import com.titan.quizgame.util.constants.Api;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

@Module
public class NetworkModule {



    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient() {

        OkHttpClient client = new OkHttpClient.Builder()
/*
                //establish connection to server
                .connectTimeout(ApiConstants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)

                //time between each byte read from the server
                .readTimeout(ApiConstants.READ_TIMEOUT, TimeUnit.SECONDS)

                //time between each byte sent to server
                .writeTimeout(ApiConstants.WRITE_TIMEOUT, TimeUnit.SECONDS)
*/
                .retryOnConnectionFailure(false)
                .addInterceptor(new ApiInterceptor())
                .build();

        Timber.d("Providing OkHttpClient: " + client);
        return client;
    }


    @Provides
    @Singleton
    Retrofit provideRetrofit(OkHttpClient okHttpClient) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.IMGUR_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Timber.d("Providing retrofit: " + retrofit);
        return retrofit;
    }



    @Provides
    @Singleton
    ImgurApi provideImgurApiInterface(Retrofit retrofit) {

        ImgurApi api = retrofit.create(ImgurApi.class);
        Timber.d("Providing ImgurApi: " + api);

        return api;
    }


}
