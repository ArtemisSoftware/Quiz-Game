package com.titan.quizgame;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class QuizModule {

    /*
    @Singleton
    @Provides
    static NoteDataBase provideNoteDatabase(Application application){
        return Room.databaseBuilder(application, NoteDataBase.class, NoteDataBase.DATABASE_NAME).build();
    }


    @Singleton
    @Provides
    static QuestionDao provideQuestionDao(NoteDataBase noteDataBase){

        QuestionDao dao = noteDataBase.getNoteDao();

        Timber.d("Providing Question Dao: " + dao);

        return dao;
    }


    @Singleton
    @Provides
    static CategoryDao provideCategoryDao(NoteDataBase noteDataBase){

        CategoryDao dao = noteDataBase.getNoteDao();

        Timber.d("Providing Category Dao: " + dao);

        return dao;
    }


    @Singleton
    @Provides
    static QuizRepository provideQuizRepository(QuestionDao questionDao, CategoryDao categoryDao){

        QuizRepository repository = new QuizRepository(questionDao, categoryDao);

        Timber.d("Providing Quiz Repository: " + repository);

        return repository;
    }
    */

}
