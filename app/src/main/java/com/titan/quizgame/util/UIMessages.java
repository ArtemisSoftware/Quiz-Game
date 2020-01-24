package com.titan.quizgame.util;

import android.content.Context;
import android.graphics.Color;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;
import com.titan.quizgame.R;


public class UIMessages {


    public static void success(AwesomeSuccessDialog pDialog){



        pDialog.setTitle(R.string.app_name)
                .setMessage(R.string.app_name)
                .setColoredCircle(R.color.dialogSuccessBackgroundColor)
                .setDialogIconAndColor(R.drawable.ic_dialog_info, R.color.white)
                .setCancelable(true)
        .show();

    }

}
