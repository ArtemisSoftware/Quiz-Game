package com.titan.quizgame.util;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import androidx.core.content.FileProvider;

import com.titan.quizgame.util.constants.ActivityRequestCode;

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

    public static void openGallery(Activity context){

        Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        context.startActivityForResult(pickPhoto, ActivityRequestCode.REQUEST_GALLERY_IMAGE);

    }


    public static Bitmap getBitmap(Activity context, Uri uri){

        Bitmap bitmap = null;

        try {

                if(Build.VERSION.SDK_INT < 28) {
                    bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uri);

                }
                else {
                    ImageDecoder.Source source = ImageDecoder.createSource(context.getContentResolver(), uri);
                    bitmap = ImageDecoder.decodeBitmap(source);

                }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }



    private static Uri getCacheImagePath(Activity context, String fileName) {

        File path = new File(context.getExternalCacheDir(), "camera");

        if (!path.exists())
            path.mkdirs();

        File image = new File(path, fileName);
        return FileProvider.getUriForFile(context, context.getPackageName() + ".provider", image);
    }
}
