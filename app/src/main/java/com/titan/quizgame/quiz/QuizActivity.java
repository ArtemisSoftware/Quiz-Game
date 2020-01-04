package com.titan.quizgame.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.titan.quizgame.R;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.persistence.QuestionDao;
import com.titan.quizgame.quiz.persistence.QuestionDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class QuizActivity extends AppCompatActivity {

    @BindView(R.id.text_view_question)
    TextView textViewQuestion;

    @BindView(R.id.text_view_score)
    TextView textViewScore;

    @BindView(R.id.text_view_question_count)
    TextView textViewQuestionCount;

    @BindView(R.id.text_view_countdown)
    TextView textViewCountDown;

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

    private QuestionDao questionDao;

    private ColorStateList textColorDefaultRb;

    private List<Question> questionList;
    private int questionCounter;
    private int questionCountTotal;
    private Question currentQuestion;

    private int score;
    private boolean answered;
    private long backPressedTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        ButterKnife.bind(this);

        questionDao = QuestionDatabase.getInstance(this).questionDao();

        textColorDefaultRb = rb1.getTextColors();


        getQuestions();
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
        } else {
            finishQuiz();
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
        } else {
            buttonConfirmNext.setText("Finish");
        }
    }

    private void finishQuiz() {

        Intent resultIntent = new Intent();
        resultIntent.putExtra(ActivityCode.EXTRA_SCORE, score);
        setResult(RESULT_OK, resultIntent);
        finish();
    }



    private void getQuestions(){

        //getting flowable to subscribe consumer that will access the data from Room database.
        questionDao.getQuestions().subscribe(
                new Consumer<List<Question>>() {
                    @Override
                    public void accept(List<Question> questions) throws Exception {

                        questionList = questions;
                        questionCountTotal = questionList.size();
                        Collections.shuffle(questionList);
                        showNextQuestion();
                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }
        );

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
            finishQuiz();
        } else {
            Toast.makeText(this, "Press back again to finish", Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }
}
