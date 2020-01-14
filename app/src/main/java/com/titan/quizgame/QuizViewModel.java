package com.titan.quizgame;

import androidx.lifecycle.ViewModel;

import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.repository.QuizRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class QuizViewModel extends ViewModel {


    private final QuizRepository quizRepository;

    @Inject
    public QuizViewModel(QuizRepository quizRepository) {

        this.quizRepository = quizRepository;

        Timber.d("Quiz repository: " + this.quizRepository);
        Timber.d("QuizViewModel is ready");
    }


    public void loadCategories() {

        this.quizRepository.getCategories()
                //.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<Category>>() {
                            @Override
                            public void accept(List<Category> categories) throws Exception {

                                /*
                                ArrayAdapter<Category> adapterCategories = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, categories);
                                adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                spinnerCategory.setAdapter(adapterCategories);
                                */
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
