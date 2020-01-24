package com.titan.quizgame.database;

import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.persistence.CategoryDao;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public  class PopulateDb extends AsyncTask<Void, Void, Void> {
        private CategoryDao categoryDao;

        public PopulateDb(QuizDatabase db) {
            categoryDao = db.categoryDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<Category> categories = new ArrayList<>();
            categories.add(new Category("Programming"));
            categories.add(new Category("Geography"));
            categories.add(new Category("Math"));


            categoryDao.insertCategories(categories)
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
            return null;
        }

}
