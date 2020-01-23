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
                database.execSQL("CREATE TABLE IF NOT EXISTS 'score' ("
                        + "'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                        + "'categoryId' INTEGER NOT NULL,"
                        + "'difficulty' TEXT NOT NULL, "
                        + "'playerName' TEXT NOT NULL, "
                        + "'points' INTEGER NOT NULL, "
                        + "FOREIGN KEY ('playerName') REFERENCES 'players' ('name') ON DELETE CASCADE)");
                Timber.d("MIGRATION_2_3: success");
            }
            catch(SQLException e){
                Timber.e("erro MIGRATION_2_3: " + e.getMessage());
            }
        }
    };


    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {

                database.execSQL("DROP TABLE IF EXISTS 'score'");

                database.execSQL("CREATE TABLE IF NOT EXISTS 'score' ("
                        + "'id' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
                        + "'categoryId' INTEGER NOT NULL,"
                        + "'difficulty' TEXT NOT NULL, "
                        + "'playerName' TEXT NOT NULL, "
                        + "'points' INTEGER NOT NULL, "
                        + "FOREIGN KEY ('playerName') REFERENCES 'players' ('name') ON DELETE CASCADE)");


                database.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS 'index_score_playerName' ON score ('playerName')");
                Timber.d("MIGRATION_3_4: success");
            }
            catch(SQLException e){
                Timber.e("erro MIGRATION_3_4: " + e.getMessage());
            }
        }
    };


    public static final Migration MIGRATIONS [] =  new Migration []{
            MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4
    };

}
