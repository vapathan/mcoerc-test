package com.ass.mcoerctest.database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.ass.mcoerctest.models.Chapter;
import com.ass.mcoerctest.models.Question;
import com.ass.mcoerctest.models.ScoreCard;
import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.models.Subject;
import com.ass.mcoerctest.models.Test;


@Database(
        entities = {Question.class, Student.class,
                Test.class},
        version = 4
)
@TypeConverters(DateConverter.class)
public abstract class AppDatabase extends RoomDatabase {


    static final Migration MIGRATION_2_3 = new Migration(3, 4) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };

    public static final String DATABASE_NAME = "mhcet.db";
    public static volatile AppDatabase instance;
    private static final Object LOCK = new Object();


    public abstract QuestionDao questionDao();

    public abstract StudentDao studentDao();

    public abstract TestDao testDao();



    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LOCK) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DATABASE_NAME)
                            .addMigrations(MIGRATION_2_3)
                            .build();
                }
            }
        }
        return instance;
    }

}
