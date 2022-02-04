package com.ass.mcoerctest.repositories;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import androidx.recyclerview.widget.RecyclerView;

import com.ass.mcoerctest.Adapters.DashboardListAdapter;
import com.ass.mcoerctest.constants.Api;
import com.ass.mcoerctest.database.AppDatabase;
import com.ass.mcoerctest.models.Question;
import com.ass.mcoerctest.models.Subject;
import com.ass.mcoerctest.models.Test;
import com.ass.mcoerctest.models.TestQuestion;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;
import com.ass.mcoerctest.utilities.AppExecutor;
import com.ass.mcoerctest.utilities.ui.UIHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TestRepository {
    private static TestRepository mInstance;
    private RetrofitApi mRetrofitApi;
    private AppDatabase mDb;
    private Context mContext;

    public static TestRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new TestRepository(context);
        }
        return mInstance;
    }

    private TestRepository(Context context) {
        RetrofitApiClient retrofitApiClient = RetrofitApiClient.getInstance();
        mContext = context;
        mRetrofitApi = retrofitApiClient.getRetrofitApi();
        mDb = AppDatabase.getInstance(context);
    }


    private boolean isTestExists(int testId) {
        if (mDb.testDao().getTest(testId) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void saveTest(final Test test) {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if (!isTestExists(test.getId())) {
                    mDb.testDao().insert(test);
                }
            }
        });
    }

    public void saveTests(final List<Test> testList) {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (Test test : testList) {
                    saveTest(test);
                }
            }
        });
    }


    public List<Test> getTests() {
        return mDb.testDao().getTests();
    }

    private boolean isTestQuestionExists(TestQuestion testQuestion) {
        if (mDb.testQuestionDao().getTestQuestion(testQuestion.getId(), testQuestion.getQuestionId()) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void saveTestQuestions(List<TestQuestion> testQuestions) {
        AppExecutor.getInstance().dbExecutor().execute(() -> {
            for (TestQuestion testQuestion : testQuestions) {
                if (!isTestQuestionExists(testQuestion)) {
                    mDb.testQuestionDao().insert(testQuestion);
                }
            }

        });
    }


    public List<Question> getQuestions(int testId) {
        return mDb.testDao().getQuestions(testId);
    }

    public List<TestQuestion> getTestQuestions(int testId) {
        return mDb.testQuestionDao().getTestQuestions(testId);
    }


    public void updateTestStatus(Test test) {
        AppExecutor.getInstance().dbExecutor().execute(() -> {
           // mDb.testDao().updateTestStatus(test.getId(), test.getCurrentStatus(), test.getCurrentQuestionNumber(), test.getTimeSpent(), test.getScore());
        });
    }

    public void markTestQuestionAsAttempted(int testId, int questionId, String selectedOption) {
        AppExecutor.getInstance().dbExecutor().execute(() -> {
            mDb.testQuestionDao().markTestQuestionAsAttempted(testId, questionId, selectedOption);
        });
    }


    public List<Test> getTestList(ProgressBar progressBar) {
        //Get Tests data from remote server
        final List<Test>[] testList = new List[]{new ArrayList<>()};
        UIHelper.showProgressBar(progressBar);
        Call<Test[]> call = mRetrofitApi.getTests(Api.API_KEY, "");
        call.enqueue(new Callback<Test[]>() {
            @Override
            public void onResponse(Call<Test[]> call, Response<Test[]> response) {
                testList[0] = Arrays.asList(response.body());
                saveTests(testList[0]);

                for (Test test : testList[0]) {
                    getTestQuestionsList(test.getId());
                }
            }

            @Override
            public void onFailure(Call<Test[]> call, Throwable t) {
                testList[0] = null;
                Log.i("INFO", t.getMessage());
            }
        });
        UIHelper.hideProgressBar(progressBar);
        return testList[0];
    }


    public List<Test> getTestList(String prn , RecyclerView recyclerView, DashboardListAdapter dashboardListAdapter, ProgressBar mProgressBar) {
        //Get Subjects data from remote server
        final List<Test>[] testList = new List[]{new ArrayList<>()};
        UIHelper.showProgressBar(mProgressBar);
        Call<Test[]> call = mRetrofitApi.getTests(Api.API_KEY, prn);
        call.enqueue(new Callback<Test[]>() {
            @Override
            public void onResponse(Call<Test[]> call, Response<Test[]> response) {
                testList[0] = Arrays.asList(response.body());
                Log.i("INFO", "TTT : " + testList[0].toString());
                saveTests(testList[0]);
                dashboardListAdapter.setTests(testList[0]);
                recyclerView.setAdapter(dashboardListAdapter);
                UIHelper.hideProgressBar(mProgressBar);
            }

            @Override
            public void onFailure(Call<Test[]> call, Throwable t) {
                testList[0] = null;
                Log.i("INFO", t.getMessage());
                UIHelper.hideProgressBar(mProgressBar);
            }
        });

        return testList[0];
    }



    public List<TestQuestion> getTestQuestionsList(int testId) {
        final List<TestQuestion>[] testQuestionsLists = new List[]{new ArrayList()};
        Call<TestQuestion[]> call = mRetrofitApi.getTestQuestions(Api.API_KEY, testId);
        call.enqueue(new Callback<TestQuestion[]>() {
            @Override
            public void onResponse(Call<TestQuestion[]> call, Response<TestQuestion[]> response) {
                if (response.isSuccessful()) {
                    testQuestionsLists[0] = Arrays.asList(response.body());
                    saveTestQuestions(testQuestionsLists[0]);
                }
            }

            @Override
            public void onFailure(Call<TestQuestion[]> call, Throwable t) {
                testQuestionsLists[0] = null;
            }
        });
        return testQuestionsLists[0];
    }

}
