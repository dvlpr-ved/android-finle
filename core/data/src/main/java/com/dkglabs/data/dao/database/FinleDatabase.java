package com.dkglabs.data.dao.database;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.dkglabs.data.dao.LoanApplicantDao;
import com.dkglabs.data.dao.LoanApplicantEmiDao;
import com.dkglabs.data.entity.LoanApplicant;
import com.dkglabs.data.entity.LoanApplicantEMI;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LoanApplicant.class, LoanApplicantEMI.class}, version = 1, exportSchema = false)
public abstract class FinleDatabase extends RoomDatabase {


    public abstract LoanApplicantDao loanApplicantDao();

    private static final int NUMBER_OF_THREADS = 4;

    private static volatile FinleDatabase finleDatabaseInstance;
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static FinleDatabase getInstance(final Application application) {

        if (finleDatabaseInstance == null) {
            synchronized (FinleDatabase.class) {
                if (finleDatabaseInstance == null) {
                    finleDatabaseInstance = Room.databaseBuilder(application.getApplicationContext(), FinleDatabase.class, "FinleDatabase")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return finleDatabaseInstance;

    }

    public abstract LoanApplicantEmiDao loanApplicantEmiDao();
}
