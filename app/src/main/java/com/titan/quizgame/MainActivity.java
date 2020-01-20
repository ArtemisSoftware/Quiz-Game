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

import com.titan.quizgame.player.LeaderBoardActivity;
import com.titan.quizgame.player.PlayerProfileActivity;
import com.titan.quizgame.quiz.QuestionActivity;
import com.titan.quizgame.quiz.QuizViewModel;
import com.titan.quizgame.ui.Resource;
import com.titan.quizgame.util.Loader;
import com.titan.quizgame.util.constants.ActivityCode;
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

        viewModel.loadConfigurations();

    }



    private void subscribeObservers(){

        viewModel.observeCategories().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {


                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        spinnerCategory.setAdapter(Loader.loadCategories(getApplicationContext(), (List<Category>) resource.data));
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

                        spinnerDifficulty.setAdapter(Loader.loadStringArray(getApplicationContext(), (String[]) resource.data));
                        break;

                    case ERROR:

                        break;
                }
            }
        });

        viewModel.observeScore().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {

                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        textViewHighscore.setText(((Integer) resource.data) + "");
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
                    viewModel.loadConfigurations();
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

            case R.id.action_leader_board:

                startActivity(new Intent(MainActivity.this, LeaderBoardActivity.class));
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
