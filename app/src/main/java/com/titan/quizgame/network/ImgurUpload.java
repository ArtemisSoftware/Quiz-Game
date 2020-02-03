package com.titan.quizgame.network;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class ImgurUpload {
    public File image;
    public String title;
    public String description;
    public String albumId;


    public MultipartBody.Part getMultipartBody (){

        MultipartBody.Part body = MultipartBody.Part.createFormData(
                "image",
                image.getName(),
                RequestBody.create(MediaType.parse("image/*"), image));

        return body;
    }

}
