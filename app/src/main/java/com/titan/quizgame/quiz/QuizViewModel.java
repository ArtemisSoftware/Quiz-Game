package com.titan.quizgame.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.quizgame.util.constants.GameConstants;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.repository.QuizRepository;
import com.titan.quizgame.ui.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class QuizViewModel extends ViewModel {


    private final CompositeDisposable disposables;

    private final QuizRepository quizRepository;

    private MutableLiveData<Resource> categoriesLiveData;
    private MutableLiveData<Resource> difficultyLiveData;
    private MutableLiveData<Resource> questionsLiveData;



    @Inject
    public QuizViewModel(QuizRepository quizRepository) {

        this.quizRepository = quizRepository;
        this.disposables = new CompositeDisposable();

        categoriesLiveData = new MutableLiveData<>();
        difficultyLiveData = new MutableLiveData<>();
        questionsLiveData = new MutableLiveData<>();

        Timber.d("Quiz repository: " + this.quizRepository);
        Timber.d("QuizViewModel is ready");
    }


    public MutableLiveData<Resource> observeCategories(){
        return categoriesLiveData;
    }

    public MutableLiveData<Resource> observeDifficulty(){
        return difficultyLiveData;
    }

    public MutableLiveData<Resource> observeQuestions(){
        return questionsLiveData;
    }


    public void loadConfigurations() {

        disposables.add(
                this.quizRepository.getCategories()
                //.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<Category>>() {
                            @Override
                            public void accept(List<Category> categories) throws Exception {

                                categoriesLiveData.setValue(Resource.success(categories, ""));

                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }
                )
        );


        difficultyLiveData.setValue(Resource.success(GameConstants.getAllDifficultyLevels(), ""));

    }


    public void loadQuestions(String difficulty, int categoryID) {

        disposables.add(
        //getting flowable to subscribe consumer that will access the data from Room database.
                quizRepository.getQuestions(difficulty, categoryID)
                //.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<Question>>() {
                            @Override
                            public void accept(List<Question> questions) throws Exception {

                                questionsLiveData.setValue(Resource.success(questions, ""));

                            }
                        },
                        new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        }
                )
        );
    }


    public void saveQuestions(Question question) {
/*
        disposables.add(
                //getting flowable to subscribe consumer that will access the data from Room database.
                quizRepository.
                        //.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<Question>>() {
                                    @Override
                                    public void accept(List<Question> questions) throws Exception {

                                        questionsLiveData.setValue(Resource.success(questions, ""));

                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {

                                    }
                                }
                        )
        );
        */
    }



    @Override
    protected void onCleared() {
        disposables.clear();
    }

}
