package com.titan.quizgame;

import androidx.lifecycle.ViewModel;

import com.titan.quizgame.quiz.repository.QuizRepository;

import javax.inject.Inject;

import timber.log.Timber;

public class QuizViewModel extends ViewModel {


    private final QuizRepository quizRepository;

    @Inject
    public QuizViewModel(QuizRepository quizRepository) {

        this.quizRepository = quizRepository;


        Timber.d("Quiz repository: " + this.quizRepository);
        Timber.d("QuizViewModel is ready");

    }

}
