package com.titan.quizgame.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.titan.quizgame.player.models.Player;
import com.titan.quizgame.player.persistence.PlayerDao;
import com.titan.quizgame.quiz.models.Category;
import com.titan.quizgame.quiz.models.Question;
import com.titan.quizgame.quiz.persistence.CategoryDao;
import com.titan.quizgame.quiz.persistence.QuestionDao;
import com.titan.quizgame.util.constants.DataBase;

import java.sql.SQLException;

@Database(entities = {Question.class, Category.class, Player.class}, version = DataBase.DATABASE_VERSION)
public abstract class QuizDatabase extends RoomDatabase {

    public abstract QuestionDao questionDao();
    public abstract CategoryDao categoryDao();
    public abstract PlayerDao playerDao();

    /*

    private static QuizDatabase instance;

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
    */



/*
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
*/
}