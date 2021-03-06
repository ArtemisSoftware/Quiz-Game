package com.titan.quizgame;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.awesomedialog.blennersilva.awesomedialoglibrary.AwesomeSuccessDialog;

import dagger.android.support.DaggerAppCompatActivity;

public abstract class BaseActivity extends DaggerAppCompatActivity {

    public ProgressBar mProgressBar;
    public AwesomeSuccessDialog pDialog;

    @Override
    public void setContentView(int layoutResID) {

        ConstraintLayout constraintLayout = (ConstraintLayout) getLayoutInflater().inflate(R.layout.activity_base, null);
        FrameLayout frameLayout = constraintLayout.findViewById(R.id.activity_content);
        mProgressBar = constraintLayout.findViewById(R.id.progress_bar);

        getLayoutInflater().inflate(layoutResID, frameLayout, true);

        pDialog = new AwesomeSuccessDialog(this);


        super.setContentView(constraintLayout);
    }

    public void showProgressBar(boolean visible) {

        mProgressBar.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}