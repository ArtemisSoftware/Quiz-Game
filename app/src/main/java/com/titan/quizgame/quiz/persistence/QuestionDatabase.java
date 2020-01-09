package com.titan.quizgame.quiz.persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.titan.quizgame.quiz.GameConstants;
import com.titan.quizgame.quiz.models.Question;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

@Database(entities = {Question.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {

    private static QuestionDatabase instance;

    public abstract QuestionDao questionDao();

    public static synchronized QuestionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), QuestionDatabase.class, "questions_database")
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
            database.execSQL("ALTER TABLE questions ADD COLUMN difficulty TEXT");
        }
    };
*/


    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            QuestionDao questionDao = instance.questionDao();

            List<Question> questions = new ArrayList<>();

            questions.add(new Question("A is correct", "A", "B", "C", 1, GameConstants.DIFFICULTY_EASY));
            questions.add(new Question("C is correct", "A", "B", "C", 3, GameConstants.DIFFICULTY_HARD));
            questions.add(new Question("B is correct", "A", "B", "C", 2, GameConstants.DIFFICULTY_EASY));

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