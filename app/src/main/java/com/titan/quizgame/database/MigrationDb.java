package com.titan.quizgame.database;


import android.database.SQLException;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import timber.log.Timber;

public class MigrationDb {


    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS 'players' ('name' TEXT PRIMARY KEY)");
                Timber.d("MIGRATION_1_2: success");
            }
            catch(SQLException e){
                Timber.e("erro MIGRATION_1_2: " + e.getMessage());
            }
        }
    };


    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS 'score' ('categoryId' INTEGER PRIMARY KEY,"
                        + "'difficulty' TEXT PRIMARY KEY, "
                        + "'points' INTEGER NOT NULL, "
                        + "FOREIGN KEY ('playerName') REFERENCES players ('name'))");
                Timber.d("MIGRATION_2_3: success");
            }
            catch(SQLException e){
                Timber.e("erro MIGRATION_2_3: " + e.getMessage());
            }
        }
    };


    public static final Migration MIGRATIONS [] =  new Migration []{
            MIGRATION_1_2, MIGRATION_2_3
    };

}
