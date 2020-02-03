package com.titan.quizgame.network;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ImgurApi {

    @Multipart
    @Headers({
            "Authorization: Client-ID 0b5e46b0ac7b39f"
    })
    @POST("image")
    Observable<ImageResponse> postImage(
            @Query("title") String title,
            @Query("description") String description,
            @Query("album") String albumId,
            @Query("account_url") String username,
            @Part MultipartBody.Part file);
}
