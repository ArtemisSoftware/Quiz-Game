package com.titan.quizgame.quiz.persistence;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.titan.quizgame.quiz.models.Question;

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
            questionDao.insertQuestions(new Question("A is correct", "A", "B", "C", 1));
            questionDao.insertQuestions(new Question("B is correct", "A", "B", "C", 2));
            questionDao.insertQuestions(new Question("C is correct", "A", "B", "C", 3));
            questionDao.insertQuestions(new Question("AA is correct", "A", "B", "C", 1));
            questionDao.insertQuestions(new Question("BB is correct", "A", "B", "C", 2));
            return null;
        }
    }
}