package com.titan.quizgame.network;

import com.titan.quizgame.util.constants.Api;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import timber.log.Timber;

public class ApiInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        Request newRequest = request.newBuilder()
                .addHeader(Api.HEADER_AUTHONRIZATION, "Client-ID " + Api.CLIENT_ID)
                .build();

        Timber.d("Intercepting OkHttpClient: " + newRequest);
        return chain.proceed(newRequest);
    }

}