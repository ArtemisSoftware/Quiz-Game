package com.titan.quizgame.quiz;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.titan.quizgame.BaseActivity;
import com.titan.quizgame.R;
import com.titan.quizgame.player.models.Player;
import com.titan.quizgame.player.models.Score;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.ui.Resource;
import com.titan.quizgame.util.constants.ActivityCode;
import com.titan.quizgame.util.constants.GameConstants;
import com.titan.quizgame.util.viewmodel.ViewModelProviderFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import timber.log.Timber;

public class QuizActivity extends BaseActivity {

    @BindView(R.id.text_view_question)
    TextView textViewQuestion;

    @BindView(R.id.text_view_score)
    TextView textViewScore;

    @BindView(R.id.text_view_question_count)
    TextView textViewQuestionCount;

    @BindView(R.id.text_view_countdown)
    TextView textViewCountDown;

    @BindView(R.id.text_view_difficulty)
    TextView textViewDifficulty;

    @BindView(R.id.text_view_category)
    TextView textViewCategory;

    @BindView(R.id.radio_group)
    RadioGroup rbGroup;

    @BindView(R.id.radio_button1)
    RadioButton rb1;

    @BindView(R.id.radio_button2)
    RadioButton rb2;

    @BindView(R.id.radio_button3)
    RadioButton rb3;

    @BindView(R.id.button_confirm_next)
    Button buttonConfirmNext;



    private ColorStateList textColorDefaultRb;
    private ColorStateList textColorDefaultCd;



    private int questionCountTotal;
    private Question currentQuestion;
    private long backPressedTime;
    private CountDownTimer countDownTimer;


    private ArrayList<Question> questionList;
    private static final String KEY_QUESTION_LIST = "keyQuestionList";


    private int score;
    private static final String KEY_SCORE = "keyScore";

    private int questionCounter;
    private static final String KEY_QUESTION_COUNT = "keyQuestionCount";

    private long timeLeftInMillis;
    private static final String KEY_MILLIS_LEFT = "keyMillisLeft";

    private boolean answered;
    private static final String KEY_ANSWERED = "keyAnswered";




    private QuizViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(this, providerFactory).get(QuizViewModel.class);

        textColorDefaultRb = rb1.getTextColors();
        textColorDefaultCd = textViewCountDown.getTextColors();


        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(ActivityCode.EXTRA_DIFFICULTY);
        Category category = intent.getExtras().getParcelable(ActivityCode.EXTRA_CATEGORY);


        textViewDifficulty.setText("Difficulty: " + difficulty);
        textViewCategory.setText("Category: " + category.getName());

        subscribeObservers();


        if(savedInstanceState == null) {
            viewModel.loadQuestions(difficulty, category.getId());
        }
        else{
            questionList = savedInstanceState.getParcelableArrayList(KEY_QUESTION_LIST);
            questionCountTotal = questionList.size();
            questionCounter = savedInstanceState.getInt(KEY_QUESTION_COUNT);
            currentQuestion = questionList.get(questionCounter - 1);

            score = savedInstanceState.getInt(KEY_SCORE);
            timeLeftInMillis = savedInstanceState.getLong(KEY_MILLIS_LEFT);
            answered = savedInstanceState.getBoolean(KEY_ANSWERED);

            if (!answered) {
                startCountDown();
            } else {
                updateCountDownText();
                showSolution();
            }
        }
    }

    private void subscribeObservers() {

        viewModel.observeQuestions().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {


                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        loadQuestions((List<Question>) resource.data);
                        break;

                    case ERROR:

                        break;

                }
            }
        });

        viewModel.observeQuiz().observe(this, new Observer<Resource>() {
            @Override
            public void onChanged(Resource resource) {


                Timber.d("onChanged: " + resource.toString());

                switch (resource.status){

                    case SUCCESS:

                        finishQuiz();
                        break;

                    case ERROR:

                        break;

                }
            }
        });
    }



    private void showNextQuestion() {
        rb1.setTextColor(textColorDefaultRb);
        rb2.setTextColor(textColorDefaultRb);
        rb3.setTextColor(textColorDefaultRb);
        rbGroup.clearCheck();

        if (questionCounter < questionCountTotal) {
            currentQuestion = questionList.get(questionCounter);

            textViewQuestion.setText(currentQuestion.getQuestion());
            rb1.setText(currentQuestion.getOption1());
            rb2.setText(currentQuestion.getOption2());
            rb3.setText(currentQuestion.getOption3());

            questionCounter++;
            textViewQuestionCount.setText("Question: " + questionCounter + "/" + questionCountTotal);
            answered = false;
            buttonConfirmNext.setText("Confirm");

            timeLeftInMillis = GameConstants.COUNTDOWN_IN_MILLIS;
            startCountDown();

        }
        else {

            saveScore();
        }
    }


    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis + 100, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                checkAnswer();
            }
        }.start();
    }



    private void updateCountDownText() {
        int minutes = (int) (timeLeftInMillis / 1000) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 10000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }


    private void checkAnswer() {
        answered = true;

        RadioButton rbSelected = findViewById(rbGroup.getCheckedRadioButtonId());
        int answerNr = rbGroup.indexOfChild(rbSelected) + 1;

        if (answerNr == currentQuestion.getAnswerNr()) {
            score++;
            textViewScore.setText("Score: " + score);
        }

        showSolution();
    }

    private void showSolution() {
        rb1.setTextColor(Color.RED);
        rb2.setTextColor(Color.RED);
        rb3.setTextColor(Color.RED);

        switch (currentQuestion.getAnswerNr()) {
            case 1:
                rb1.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 1 is correct");
                break;
            case 2:
                rb2.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 2 is correct");
                break;
            case 3:
                rb3.setTextColor(Color.GREEN);
                textViewQuestion.setText("Answer 3 is correct");
                break;
        }

        if (questionCounter < questionCountTotal) {
            buttonConfirmNext.setText("Next");
        }
        else {


            countDownTimer.cancel();
            buttonConfirmNext.setText("Finish");
        }
    }

    private void saveScore() {

        Intent intent = getIntent();
        String difficulty = intent.getStringExtra(ActivityCode.EXTRA_DIFFICULTY);
        Category category = intent.getExtras().getParcelable(ActivityCode.EXTRA_CATEGORY);

        viewModel.saveScore(new Player("TEST PLAYER"), new Score(score, category.getId(), difficulty, "TEST PLAYER"));
    }

    private void finishQuiz() {

        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }



    private void loadQuestions(List<Question> questions){

        questionList = (ArrayList<Question>)questions;
        questionCountTotal = questionList.size();
        Collections.shuffle(questionList);
        showNextQuestion();
    }


    @OnClick(R.id.button_confirm_next)
    public void onButtonClick(View view) {
        if (!answered) {
            if (rb1.isChecked() || rb2.isChecked() || rb3.isChecked()) {
                checkAnswer();
            } else {
                Toast.makeText(QuizActivity.this, "Please select an answer", Toast.LENGTH_SHORT).show();
            }
        } else {
            showNextQuestion();
        }
    }


    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            saveScore();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_SCORE, score);
        outState.putInt(KEY_QUESTION_COUNT, questionCounter);
        outState.putLong(KEY_MILLIS_LEFT, timeLeftInMillis);
        outState.putBoolean(KEY_ANSWERED, answered);
        outState.putParcelableArrayList(KEY_QUESTION_LIST, questionList);
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
