package com.titan.quizgame.quiz.persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.titan.quizgame.quiz.models.Question;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

@Database(entities = {Question.class}, version = 1)
public abstract class QuestionDatabase extends RoomDatabase {

    private static QuestionDatabase instance;

    public abstract QuestionDao questionDao();

    public static synchronized QuestionDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    QuestionDatabase.class, "questions_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private QuestionDao questionDao;

        private PopulateDbAsyncTask(QuestionDatabase db) {
            questionDao = db.questionDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            List<Question> questions = new ArrayList<>();
            questions.add(new Question("A is correct", "A", "B", "C", 1));
            questions.add(new Question("C is correct", "A", "B", "C", 3));

            //inserting records
            questionDao.insertQuestions(questions).subscribe(
                    new Consumer<long[]>() {

                        @Override
                        public void accept(long[] longs) throws Exception {

                        }
                    }
                    ,
                    new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {

                        }
                    });

            return null;
        }
    }
}