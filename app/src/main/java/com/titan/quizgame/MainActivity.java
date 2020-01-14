package com.titan.quizgame;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.titan.quizgame.player.PlayerProfileActivity;
import com.titan.quizgame.quiz.QuestionActivity;
import com.titan.quizgame.ui.Resource;
import com.titan.quizgame.util.ActivityCode;
import com.titan.quizgame.quiz.QuizActivity;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.settings.SettingsActivity;
import com.titan.quizgame.util.viewmodel.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class MainActivity extends BaseActivity {

    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighscore";
    private int highscore;



    @BindView(R.id.text_view_highscore)
    TextView textViewHighscore;

    @BindView(R.id.spinner_difficulty)
    Spinner spinnerDifficulty;

    @BindView(R.id.spinner_category)
    Spinner spinnerCategory;



    private QuizViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        viewModel = ViewModelProviders.of(this, providerFactory).get(QuizViewModel.class);


        subscribeObservers();

        loadHighscore();

        viewModel.loadConfigurations();

    }



    private void subscribeObservers(){

        viewModel.observeCategories().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {


                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        loadCategories((List<Category>) resource.data);
                        break;

                    case ERROR:

                        break;

                }
            }
        });

        viewModel.observeDifficulty().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {


                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        loadDifficultyLevels((String[]) resource.data);
                        break;

                    case ERROR:

                        break;

                }

            }
        });

    }





    private void startQuiz() {

        Intent intent = new Intent(this, QuizActivity.class);
        intent.putExtra(ActivityCode.EXTRA_DIFFICULTY, spinnerDifficulty.getSelectedItem().toString());
        intent.putExtra(ActivityCode.EXTRA_CATEGORY, (Category) spinnerCategory.getSelectedItem());
        startActivityForResult(intent, ActivityCode.REQUEST_CODE_QUIZ);
    }



    private void loadHighscore() {
        SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
        highscore = prefs.getInt(KEY_HIGHSCORE, 0);
        textViewHighscore.setText(highscore + "");

    }

    private void loadCategories(List<Category> categories) {

        ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(adapterCategories);
    }

    private void loadDifficultyLevels(String[] difficulties) {

        ArrayAdapter<String> adapterDifficulty = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, difficulties);
        adapterDifficulty.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDifficulty.setAdapter(adapterDifficulty);
    }



    private void updateHighscore(int highscoreNew) {
        highscore = highscoreNew;
        textViewHighscore.setText(highscore + "");

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
        Intent intent = new Intent(getApplicationContext(), /*IntroActivity*/PlayerProfileActivity.class);
        startActivityForResult(intent, 201);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){

            case ActivityCode.REQUEST_CODE_QUIZ:

                if (resultCode == RESULT_OK) {
                    int score = data.getIntExtra(ActivityCode.EXTRA_SCORE, 0);
                    if (score > highscore) {
                        updateHighscore(score);
                    }
                }
                break;


            case ActivityCode.REQUEST_CODE_CONTRIBUTE:

                if (resultCode == RESULT_OK) {
                    //toast success
                }
                break;


            default:
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.action_settings:

                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                break;

            case R.id.action_contribute:

                startActivityForResult(new Intent(MainActivity.this, QuestionActivity.class), ActivityCode.REQUEST_CODE_CONTRIBUTE);
                break;


            default:
                break;
        }


        return super.onOptionsItemSelected(item);
    }
}
