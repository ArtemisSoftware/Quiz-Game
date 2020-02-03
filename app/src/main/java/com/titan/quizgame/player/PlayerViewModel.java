package com.titan.quizgame.player;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.titan.quizgame.network.ImageResponse;
import com.titan.quizgame.network.ImgurUpload;
import com.titan.quizgame.player.models.Board;
import com.titan.quizgame.player.models.Player;
import com.titan.quizgame.player.models.Score;
import com.titan.quizgame.player.repository.PlayerRepository;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.ui.Resource;
import com.titan.quizgame.util.UIMessages;

import org.reactivestreams.Subscriber;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import timber.log.Timber;

public class PlayerViewModel extends ViewModel {

    private final CompositeDisposable disposables;
    private final PlayerRepository playerRepository;


    private MutableLiveData<Resource> playersLiveData;

    @Inject
    public PlayerViewModel(PlayerRepository playerRepository) {

        this.playerRepository = playerRepository;
        this.disposables = new CompositeDisposable();

        playersLiveData = new MutableLiveData<>();

        Timber.d("Player repository: " + this.playerRepository);
        Timber.d("PlayerViewModel is ready");
    }

    public MutableLiveData<Resource> observePlayers(){
        return playersLiveData;
    }




    public void saveScore(ImgurUpload upload, Player player, Score score) {

        playerRepository.playerExists(player.getName())
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
                            action = Completable.concatArray(playerRepository.savePlayer(player), playerRepository.saveScore(score));
                        }
                        else {
                            action = Completable.concatArray(playerRepository.saveScore(score));
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
                        playersLiveData.setValue(Resource.success(null, "Score saved"));
                    }

                    @Override
                    public void onError(Throwable throwable) {

                        Timber.d("onError." + Thread.currentThread().toString());
                        playersLiveData.setValue(Resource.error(throwable.getMessage(), "Execution error"));
                    }
                });

    }


    public void postPlayerImage(String name, String description, String albumId, String username, MultipartBody.Part file) {

        playerRepository.postPlayerImage(name, description, albumId, username, file)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .unsubscribeOn(Schedulers.io())
                .subscribe(new Observer<ImageResponse>() {
                    @Override
                    public void onSubscribe(Disposable s) {

                    }

                    @Override
                    public void onNext(ImageResponse initPost) {

                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }





    /* all together
    public void searchGallery(String nsid) {
        Timber.d("Searching user " + nsid + " page " + pageNumber + " list of pictures");
        isPerformingQuery = true;
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
    }
    private Observable<List<Picture>> getPicturesObservable(List<String> photoIds){
        List<Observable<PhotoResponse>> requests = new ArrayList<>();
        for(String id : photoIds) {
            requests.add(repository.searchPhoto(id));
        }
        Observable<List<Picture>> observable = Observable.zip(
                requests,
                new Function<Object[], List<Picture>>() {
                    @Override
                    public List<Picture> apply(Object[] photos) throws Exception {
                        Timber.d("apply photo response: " + photos);
                        List<Picture> pictures = new ArrayList<>();
                        //for (PhotoResponse photo : photos) {
                        for (int i = 0; i < photos.length; ++i) {
                            PhotoResponse photo = ((PhotoResponse) photos[i]);
                            pictures.add(new Picture(photo));
                        }
                        return pictures;
                    }
                });
        return observable;
    }
    */




    public void loadLeaderBoard() {

        disposables.add(
                //getting flowable to subscribe consumer that will access the data from Room database.
                playerRepository.getLeaderBoard()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<List<Board>>() {
                                    @Override
                                    public void accept(List<Board> board) throws Exception {

                                        if(board.size() > 0) {
                                            playersLiveData.setValue(Resource.success(board, ""));
                                        }
                                        else{
                                            playersLiveData.setValue(Resource.error("No scores available", "Leader Board"));
                                        }

                                    }
                                },
                                new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        playersLiveData.setValue(Resource.error(throwable.getMessage(), "Execution error"));
                                    }
                                }
                        )
        );
    }



    @Override
    protected void onCleared() {
        disposables.clear();
    }

}
