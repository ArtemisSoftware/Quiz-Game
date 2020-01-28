package com.titan.quizgame.database;

import android.content.ContentValues;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.persistence.CategoryDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public  class PopulateDb {

        private QuizDatabase db;

        public PopulateDb(QuizDatabase db) {
            this.db = db;
        }


        public void populate(){

            db.categoryDao().insertCategories(getCategories())
                    .subscribeOn(Schedulers.io())
                    //.observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            new Consumer<long[]>() {

                                @Override
                                public void accept(long[] longs) throws Exception {

                                }
                            },
                            new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {

                                }
                            });
    }


    private List<Category> getCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Programming"));
        categories.add(new Category("Geography"));
        categories.add(new Category("Math"));

        return categories;
    }

}
