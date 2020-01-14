package com.titan.quizgame;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.quizgame.quiz.GameConstants;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.repository.QuizRepository;
import com.titan.quizgame.ui.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class QuizViewModel extends ViewModel {


    private final CompositeDisposable disposables;

    private final QuizRepository quizRepository;

    private MutableLiveData<Resource> categoriesLiveData;
    private MutableLiveData<Resource> difficultyLiveData;



    @Inject
    public QuizViewModel(QuizRepository quizRepository) {

        this.quizRepository = quizRepository;
        this.disposables = new CompositeDisposable();
        categoriesLiveData = new MutableLiveData<>();
        difficultyLiveData = new MutableLiveData<>();

        Timber.d("Quiz repository: " + this.quizRepository);
        Timber.d("QuizViewModel is ready");
    }


    public MutableLiveData<Resource> observeCategories(){
        return categoriesLiveData;
    }

    public MutableLiveData<Resource> observeDifficulty(){
        return difficultyLiveData;
    }


    public void loadCategories() {

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


    @Override
    protected void onCleared() {
        disposables.clear();
    }

}
