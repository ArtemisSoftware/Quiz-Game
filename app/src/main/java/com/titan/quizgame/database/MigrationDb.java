package com.titan.quizgame.database;


import android.database.SQLException;

import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class MigrationDb {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            try {
                database.execSQL("CREATE TABLE IF NOT EXISTS 'players' ('name' TEXT PRIMARY KEY)");
            }
            catch(SQLException e){

            }
        }
    };


    public static final Migration MIGRATIONS [] =  new Migration []{
            MIGRATION_1_2
    };

}
