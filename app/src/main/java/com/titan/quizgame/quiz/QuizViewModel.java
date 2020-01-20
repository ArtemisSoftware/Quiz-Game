package com.titan.quizgame.quiz;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.quizgame.player.models.Player;
import com.titan.quizgame.player.models.Score;
import com.titan.quizgame.util.constants.GameConstants;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.repository.QuizRepository;
import com.titan.quizgame.ui.Resource;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import timber.log.Timber;

public class QuizViewModel extends ViewModel {


    private final CompositeDisposable disposables;

    private final QuizRepository quizRepository;

    private MutableLiveData<Resource> categoriesLiveData;
    private MutableLiveData<Resource> difficultyLiveData;
    private MutableLiveData<Resource> questionsLiveData;
    private MutableLiveData<Resource> quizLiveData;
    private MutableLiveData<Resource> scoreLiveData;



    @Inject
    public QuizViewModel(QuizRepository quizRepository) {

        this.quizRepository = quizRepository;
        this.disposables = new CompositeDisposable();

        categoriesLiveData = new MutableLiveData<>();
        difficultyLiveData = new MutableLiveData<>();
        questionsLiveData = new MutableLiveData<>();
        quizLiveData = new MutableLiveData<>();
        scoreLiveData = new MutableLiveData<>();

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

    public MutableLiveData<Resource> observeQuiz(){
        return quizLiveData;
    }

    public MutableLiveData<Resource> observeScore(){
        return scoreLiveData;
    }


    public void loadConfigurations() {

        //quizRepository.insert(new Category("Math"));

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


        disposables.add(
                this.quizRepository.getHighScore()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<Integer>() {
                                    @Override
                                    public void accept(Integer score) throws Exception {

                                        scoreLiveData.setValue(Resource.success(score, ""));

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


    public void loadQuestions(String difficulty, int categoryID) {

        disposables.add(
        //getting flowable to subscribe consumer that will access the data from Room database.
                quizRepository.getQuestions(difficulty, categoryID)
                .subscribeOn(Schedulers.io())
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

        quizRepository.saveQuestion(question)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if(d != null) {
                            disposables.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        questionsLiveData.setValue(Resource.success(null, "Data saved"));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }


    public void saveScore(Player player, Score score) {

        quizRepository.playerExists(player.getName())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer response) throws Exception {

                        return response; // B.
                    }
                })
                .flatMapCompletable(new Function<Integer, Completable>() {
                    @Override
                    public Completable apply(Integer response) throws Exception {

                        Completable action;

                        if (response == 0) { //nao existe
                            action = Completable.concatArray(quizRepository.savePlayer(player), quizRepository.saveScore(score));
                        } else {
                            action = Completable.concatArray(quizRepository.saveScore(score));
                        }

                        return action;
                    }
                })
                .doOnSubscribe(__ -> {
                    //Log.w(LOG_TAG, "Begin transaction. " + Thread.currentThread().toString());
                    //mRoomDatabase.beginTransaction();
                })
                .doOnComplete(() -> {
                    //Log.w(LOG_TAG, "Set transaction successful."  + Thread.currentThread().toString());
                    //mRoomDatabase.setTransactionSuccessful();
                })
                .doFinally(new Action() {
                    @Override
                    public void run() throws Exception {
                        Timber.d("End transaction." + Thread.currentThread().toString());
                        //mRoomDatabase.endTransaction();
                    }
                })
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread()) // ON UI THREAD
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        if (d != null) {
                            disposables.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        Timber.d("onComplete." + Thread.currentThread().toString());
                        quizLiveData.setValue(Resource.success(null, "Score saved"));
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(LOG_TAG, "onError." + Thread.currentThread().toString());
                    }
                });



/*
        Completable.concatArray(quizRepository.savePlayer(new Player("PLAYER 1")), quizRepository.savePlayer(new Player("PLAYER 2")))
                .observeOn(Schedulers.single()) // OFF UI THREAD
                .doOnSubscribe(__ -> {
                    //Log.w(LOG_TAG, "Begin transaction. " + Thread.currentThread().toString());
                    //mRoomDatabase.beginTransaction();
                })
                .doOnComplete(() -> {
                    //Log.w(LOG_TAG, "Set transaction successful."  + Thread.currentThread().toString());
                    //mRoomDatabase.setTransactionSuccessful();
                })
                .doFinally(
                        new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        }

                        //() -> {
                        //Log.w(LOG_TAG, "End transaction."  + Thread.currentThread().toString());
                        //mRoomDatabase.endTransaction();
                        //}
                )
                .subscribeOn(Schedulers.single())
                .observeOn(AndroidSchedulers.mainThread()) // ON UI THREAD
                .subscribeWith(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        //Log.w(LOG_TAG, "onSubscribe."  + Thread.currentThread().toString());
                    }

                    @Override
                    public void onComplete() {
                        //Log.w(LOG_TAG, "onComplete."  + Thread.currentThread().toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        //Log.e(LOG_TAG, "onError." + Thread.currentThread().toString());
                    }
                });
*/

        /*
        quizRepository.playerExists(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<Integer, Integer>() {
                    @Override
                    public Integer apply(Integer response) throws Exception {

                        return 9; // B.
                    }
                })

                .subscribe(new SingleObserver<Integer>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(Integer integer) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
        */
        /*
        repository.searchPhotoList(nsid, String.valueOf(pageNumber))
                .map(new Function<PhotoListResponse, List<String>>() {
                    @Override
                    public List<String> apply(PhotoListResponse response) throws Exception {
                        List<String> photoIds = new ArrayList<>();
                        pages = response.photos.pages;
                        for (PhotoListResponse.Photo photo : response.photos.pictures) {
                            photoIds.add(photo.id);
                        }
                        return photoIds; // B.
                    }
                })
                .flatMap(new Function<List<String>, Observable<List<Picture>>>() {
                    @Override
                    public Observable<List<Picture>> apply(List<String> photoIds) throws Exception {
                        return getPicturesObservable(photoIds);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new Observer<List<Picture>>() {
                    @Override
                    public void onSubscribe(Disposable disposable) {
                        disposables.add(disposable);
                        galleryLiveData.setValue(ApiResponse.loading());
                    }
                    @Override
                    public void onNext(List<Picture> pictures) {
                        Timber.d("onNext: " + pictures.toString());
                        galleryLiveData.setValue(ApiResponse.success(pictures));
                    }
                    @Override
                    public void onError(Throwable throwable) {
                        Timber.e("Error on serch user: " + throwable.getMessage());
                        galleryLiveData.setValue(ApiResponse.error(throwable.getMessage()));
                    }
                    @Override
                    public void onComplete() {
                        disposables.clear();
                        isPerformingQuery = false;
                    }
                });
        */












/*
        quizRepository.saveScore(score)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        if(d != null) {
                            disposables.add(d);
                        }
                    }

                    @Override
                    public void onComplete() {
                        questionsLiveData.setValue(Resource.success(null, "Score saved"));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                })
        ;
*/

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
