package com.titan.quizgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences settings = getSharedPreferences(getPackageName(),0);

        boolean firstRun = settings.getBoolean("firstRun",true);

        if(firstRun == true){//if running for first time Splash will load for first time

            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("firstRun", false);
            editor.commit();


            handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    initMainActivity();
                }
            },2000);
        }
        else{
            initMainActivity();
        }
    }


    private void initMainActivity(){
        Intent intent=new Intent(SplashActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
