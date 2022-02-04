package com.ass.mcoerctest.utilities;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AppExecutor {
    private static AppExecutor mInstance;
    private Executor dbExecutor;
    private Executor uiExecutor;

    private AppExecutor(Executor dbExecutor) {
        this.dbExecutor = dbExecutor;

    }

    public static AppExecutor getInstance() {
        if (mInstance == null) {
            mInstance = new AppExecutor(Executors.newSingleThreadExecutor());
        }
        return mInstance;
    }

    public Executor dbExecutor() {
        return dbExecutor;
    }

}
