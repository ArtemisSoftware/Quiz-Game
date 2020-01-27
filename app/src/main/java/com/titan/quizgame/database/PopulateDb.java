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

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public  class PopulateDb extends AsyncTask<Void, Void, Void> {
        private CategoryDao categoryDao;
    SupportSQLiteDatabase db;
/*
        public PopulateDb(QuizDatabase db) {
            categoryDao = db.categoryDao();
        }
*/

    public PopulateDb(SupportSQLiteDatabase db) {
        this.db = db;
    }

        @Override
        protected Void doInBackground(Void... voids) {
            fillCategoriesTable();
        /*
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
            */
            return null;
        }


    private void fillCategoriesTable() {
        Category c1 = new Category("Programming");
        insertCategory(c1);
        Category c2 = new Category("Geography");
        insertCategory(c2);
        Category c3 = new Category("Math");
        insertCategory(c3);
    }

    private void insertCategory(Category category) {
        ContentValues cv = new ContentValues();
        cv.put("name", category.getName());
        db.insert("categories", 0, cv);
    }
}
