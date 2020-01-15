package com.titan.quizgame.util.constants;

import android.Manifest;

public class Constants {


    public static String[] APP_PERMISSIONS = new String[]{
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_FINE_LOCATION
    };


    public static String[] IMAGE_PERMISSIONS = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
}
