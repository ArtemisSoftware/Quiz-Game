package com.titan.quizgame.util;

import android.app.Activity;
import android.content.Context;

import com.labters.lottiealertdialoglibrary.DialogTypes;
import com.labters.lottiealertdialoglibrary.LottieAlertDialog;

public class UIMessages {


    public static void success(Activity activity){

        LottieAlertDialog alertDialog = new LottieAlertDialog.Builder(activity, DialogTypes.TYPE_SUCCESS)
                .setTitle("Register")
                .setDescription("Player and score saved")
                .build();
        alertDialog.setCancelable(true);
        alertDialog.show();


    }

}
