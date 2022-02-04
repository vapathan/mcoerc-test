package com.ass.mcoerctest.repositories;

import android.content.Context;

import com.ass.mcoerctest.database.AppDatabase;
import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;
import com.ass.mcoerctest.utilities.AppExecutor;


public class StudentRepository {
    private static StudentRepository mInstance;
    private RetrofitApi mRetrofitApi;
    private AppDatabase mDb;
    private Context mContext;

    public static StudentRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new StudentRepository(context);
        }
        return mInstance;
    }

    private StudentRepository(Context context) {
        RetrofitApiClient retrofitApiClient = RetrofitApiClient.getInstance();
        mContext = context;
        mRetrofitApi = retrofitApiClient.getRetrofitApi();
        mDb = AppDatabase.getInstance(context);
    }

    public Student getStudent() {
        return mDb.studentDao().getStudent();
    }


    public void saveStudent(final Student student) {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {

                    mDb.studentDao().insert(student);

            }
        });
    }



}
