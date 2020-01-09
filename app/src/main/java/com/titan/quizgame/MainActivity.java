package com.titan.quizgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.titan.quizgame.quiz.ActivityCode;
import com.titan.quizgame.quiz.GameConstants;
import com.titan.quizgame.quiz.QuizActivity;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.persistence.CategoryDao;
import com.titan.quizgame.quiz.persistence.QuizDatabase;
import com.titan.quizgame.settings.SettingsActivity;
import com.titan.quizgame.sliders.IntroActivity;
import com.titan.quizgame.util.Constants;
import com.titan.quizgame.util.Permissions;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    private int highscore;

    private CategoryDao categoryDao;

    @BindView(R.id.text_view_highscore)
    TextView textViewHighscore;

    @BindView(R.id.spinner_difficulty)
    Spinner spinnerDifficulty;

    @BindView(R.id.spinner_category)
    Spinner spinnerCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        categoryDao = QuizDatabase.getInstance(this).categoryDao();


        loadCategories();
        loadDifficultyLevels();
        loadHighscore();


        //Permissions.requestAppPermission(this);

        initIntro();
    }




    private void startQuiz() {

        Category selectedCategory = (Category) spinnerCategory.getSelectedItem();

        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(ActivityCode.EXTRA_DIFFICULTY, spinnerDifficulty.getSelectedItem().toString());
        intent.putExtra(ActivityCode.EXTRA_CATEGORY_ID, selectedCategory.getId());
        intent.putExtra(ActivityCode.EXTRA_CATEGORY_NAME, selectedCategory.getName());
        startActivityForResult(intent, ActivityCode.REQUEST_CODE_QUIZ);
    }


    private void loadCategories() {

        categoryDao.getCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<Category>>() {
                            @Override
                            public void accept(List<Category> categories) throws Exception {

                                ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
                                adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCategory.setAdapter(adapterCategories);
                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }
                );

    }

    private void loadDifficultyLevels() {
        String[] difficultyLevels = GameConstants.getAllDifficultyLevels();

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficultyLevels);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
    }

    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText("Highscore: " + highscore);
    }

    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText("Highscore: " + highscore);

        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(KEY_HIGHSCORE, highscore);
        editor.apply();
    }



    @OnClick(R.id.button_start_quiz)
    public void onButtonClick(View view) {
        startQuiz();
    }





    public void initIntro(){
        Intent intent = new Intent(getApplicationContext(), IntroActivity.class);
        startActivityForResult(intent, 201);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ActivityCode.REQUEST_CODE_QUIZ) {
            if (resultCode == RESULT_OK) {
                int score = data.getIntExtra(ActivityCode.EXTRA_SCORE, 0);
                if (score > highscore) {
                    updateHighscore(score);
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            // launch settings activity
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
