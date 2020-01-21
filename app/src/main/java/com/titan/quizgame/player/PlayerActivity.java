package com.titan.quizgame.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.titan.quizgame.BaseActivity;
import com.titan.quizgame.R;

public abstract class PlayerActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);


        // Retrieve layout:
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.include_container);

        // Instantiate & use inflater:
        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = inflater.inflate(getContainerLayout(), null);

        // Clear & set new views:
        mainLayout.removeAllViews();
        mainLayout.addView(layout);
    }



    protected abstract int getContainerLayout();
}
