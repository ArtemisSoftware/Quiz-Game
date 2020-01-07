package com.titan.quizgame.quiz.persistence;

import android.content.Context;
import android.database.SQLException;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.titan.quizgame.quiz.GameConstants;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Database(entities = {Question.class, Category.class}, version = 1)
public abstract class QuizDatabase extends RoomDatabase {

    private static QuizDatabase instance;

    public abstract QuestionDao questionDao();
    public abstract CategoryDao categoryDao();

    public static synchronized QuizDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), QuizDatabase.class, "questions_database")
                    .fallbackToDestructiveMigration()
                    //.addMigrations(MIGRATION_1_2)
                    .addCallback(roomCallback)
                    .build();
        }


        return instance;
    }
/*

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {

            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS 'category' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, 'name' TEXT NOT NULL)");
                database.execSQL("ALTER TABLE 'question' ADD COLUMN 'categoryId' INTEGER NOT NULL");
            }
            catch (SQLException e){

            }
            //database.setForeignKeyConstraintsEnabled(true);

            List<Category> categories = new ArrayList<>();
            categories.add(new Category("Programming"));
            categories.add(new Category("Geography"));
            categories.add(new Category("Math"));

            CategoryDao categoryDao = instance.categoryDao();

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
        }
    };
*/


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);



            List<Category> categories = new ArrayList<>();
            categories.add(new Category("Programming"));
            categories.add(new Category("Geography"));
            categories.add(new Category("Math"));

            CategoryDao categoryDao = instance.categoryDao();

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



            QuestionDao questionDao = instance.questionDao();

            List<Question> questions = new ArrayList<>();

            questions.add(new Question("A is correct", "A", "B", "C", 1, GameConstants.DIFFICULTY_EASY, GameConstants.PROGRAMMING));
            questions.add(new Question("C is correct", "A", "B", "C", 3, GameConstants.DIFFICULTY_HARD, GameConstants.PROGRAMMING));
            questions.add(new Question("B is correct", "A", "B", "C", 2, GameConstants.DIFFICULTY_EASY, GameConstants.MATH));
            //questions.add(new Question("B is correct", "A", "B", "C", 2, GameConstants.DIFFICULTY_EASY, 4));
            //questions.add(new Question("B is correct", "A", "B", "C", 2, GameConstants.DIFFICULTY_EASY, 5));


            //inserting records
            questionDao.insertQuestions(questions)
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
    };

}