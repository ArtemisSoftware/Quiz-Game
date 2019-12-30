package com.titan.quizgame.settings;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.titan.quizgame.R;

public class SettingsActivity extends AppCompatActivity {

    public static final String KEY_PREF_EXAMPLE_SWITCH = "example_switch";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
}
