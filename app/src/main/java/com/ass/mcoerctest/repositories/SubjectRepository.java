package com.ass.mcoerctest.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.ass.mcoerctest.Adapters.DashboardListAdapter;
import com.ass.mcoerctest.constants.Api;
import com.ass.mcoerctest.database.AppDatabase;
import com.ass.mcoerctest.models.Subject;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;
import com.ass.mcoerctest.utilities.AppExecutor;
import com.ass.mcoerctest.utilities.ui.UIHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SubjectRepository {
    private static SubjectRepository mInstance;
    private RetrofitApi mRetrofitApi;
    private AppDatabase mDb;
    private Context context;

    public static SubjectRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SubjectRepository(context);
        }
        return mInstance;
    }

    private SubjectRepository(Context context) {
        RetrofitApiClient retrofitApiClient = RetrofitApiClient.getInstance();
        this.context = context;
        mRetrofitApi = retrofitApiClient.getRetrofitApi();
        mDb = AppDatabase.getInstance(context);
    }


    private boolean isSubjectExists(int subjectCode) {
        if (mDb.subjectDao().getSubject(subjectCode) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void saveSubject(final Subject subject) {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if (!isSubjectExists(subject.getCode())) {
                    mDb.subjectDao().insert(subject);
                }
            }
        });
    }

    public void saveSubjects(final List<Subject> subjectList) {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (Subject subject : subjectList) {
                    saveSubject(subject);
                }
            }
        });

    }


    public List<Subject> getSubjects() {
        return mDb.subjectDao().getSubjects();
    }


    public List<Subject> getSubjectList(RecyclerView recyclerView, DashboardListAdapter dashboardListAdapter, ProgressBar mProgressBar) {
        //Get Subjects data from remote server
        final List<Subject>[] subjectList = new List[]{new ArrayList<>()};
        UIHelper.showProgressBar(mProgressBar);
        Call<Subject[]> call = mRetrofitApi.getSubjects(Api.API_KEY);
        call.enqueue(new Callback<Subject[]>() {
            @Override
            public void onResponse(Call<Subject[]> call, Response<Subject[]> response) {
                subjectList[0] = Arrays.asList(response.body());
                Log.i("INFO", "TTT : " + subjectList[0].toString());
                saveSubjects(subjectList[0]);

                ChapterRepository.getInstance(context).getChapterList();

               // dashboardListAdapter.setTests(subjectList[0]);
                recyclerView.setAdapter(dashboardListAdapter);
                UIHelper.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<Subject[]> call, Throwable t) {
                subjectList[0] = null;
                Log.i("INFO", t.getMessage());
                UIHelper.hideProgressBar(mProgressBar);
            }
        });

        return subjectList[0];
    }

    public List<Subject> getSubjectList1() {
        //Get Subjects data from remote server
        final List<Subject>[] SubjectList = new List[]{new ArrayList<>()};

        Call<Subject[]> call = mRetrofitApi.getSubjects(Api.API_KEY);
        Subject[] Subjects = null;

        try {
            Subjects = call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return Arrays.asList(Subjects);
    }
}
