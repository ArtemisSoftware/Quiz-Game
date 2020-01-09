package com.titan.quizgame.util;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.titan.quizgame.player.ImagePickerActivity;

import java.io.File;

public class Image {


    public static void openCamera(Activity context, int activityCode) {

        String fileName = System.currentTimeMillis() + ".jpg";

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        /*
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCacheImagePath(context, fileName));
        if (intent.resolveActivity(context.getPackageManager()) != null) {

        }
        */
        context.startActivityForResult(intent, activityCode);

    }


    private static Uri getCacheImagePath(Activity context, String fileName) {

        File path = new File(context.getExternalCacheDir(), "camera");

        if (!path.exists())
            path.mkdirs();

        File image = new File(path, fileName);
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", image);
    }
}
