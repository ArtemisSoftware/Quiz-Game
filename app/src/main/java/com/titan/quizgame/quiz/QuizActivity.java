package com.titan.quizgame.quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.titan.quizgame.R;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.persistence.QuestionDao;
import com.titan.quizgame.quiz.persistence.QuestionDatabase;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class QuizActivity extends AppCompatActivity {

    private QuestionDao questionDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        questionDao = QuestionDatabase.getInstance(this).questionDao();

        getQuestions();
    }


    private void getQuestions(){

        //getting flowable to subscribe consumer that will access the data from Room database.
        questionDao.getQuestions().subscribe(
                new Consumer<List<Question>>() {
                    @Override
                    public void accept(List<Question> questions) throws Exception {

                    }
                },
                new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {

                    }
                }
        );

    }

}
