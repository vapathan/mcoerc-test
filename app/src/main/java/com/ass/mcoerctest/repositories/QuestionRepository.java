package com.ass.mcoerctest.repositories;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.ass.mcoerctest.Adapters.ChapterListAdapter;
import com.ass.mcoerctest.constants.Api;
import com.ass.mcoerctest.database.AppDatabase;
import com.ass.mcoerctest.models.Chapter;
import com.ass.mcoerctest.models.Question;
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


public class QuestionRepository {
    private static QuestionRepository mInstance;
    private RetrofitApi mRetrofitApi;
    private AppDatabase mDb;
    private Context mContext;

    public static QuestionRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new QuestionRepository(context);
        }
        return mInstance;
    }

    private QuestionRepository(Context context) {
        RetrofitApiClient retrofitApiClient = RetrofitApiClient.getInstance();
        mContext = context;
        mRetrofitApi = retrofitApiClient.getRetrofitApi();
        mDb = AppDatabase.getInstance(context);
    }

    public Question getQuestion(int questionId) {
        return mDb.questionDao().getQuestion(questionId);
    }


    private boolean isQuestionExists(int questionId) {
        if (mDb.questionDao().getQuestion(questionId) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void saveQuestion(final Question question) {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if (!isQuestionExists(question.getId())) {
                    mDb.questionDao().insert(question);
                }
            }
        });
    }

    public void saveQuestions(int testId, final List<Question> questionList) {

        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                mDb.questionDao().deleteTestQuestions(testId);
                for (Question question : questionList) {
                    saveQuestion(question);
                }
            }
        });

    }

    public void visitQuestion(final int questionId) {
        AppExecutor.getInstance().dbExecutor().execute(() -> {
            mDb.questionDao().visitQuestion(questionId);
        });
    }



    public List<Question> getQuestions(int testId) {
        return mDb.questionDao().getQuestions(testId);
    }


    public List<Integer> getQuestionIds(int subjectCode, int chapterId) {
        return mDb.questionDao().getQuestionIds(subjectCode, chapterId);
    }


    public List<Question> getQuestionList(int testId, ProgressBar progressBar) {
        //Get Questions data from remote server
        final List<Question>[] questionList = new List[]{new ArrayList<>()};

        //Animations.blink(mContext, imageView);
        if (progressBar != null) {
            UIHelper.showProgressBar(progressBar);
        }
        Call<Question[]> call = mRetrofitApi.getQuestions(Api.API_KEY, testId);
        call.enqueue(new Callback<Question[]>() {
            @Override
            public void onResponse(Call<Question[]> call, Response<Question[]> response) {
                questionList[0] = Arrays.asList(response.body());
                Log.i("INFO", "TTT : " + questionList[0].toString());
                saveQuestions(testId, questionList[0]);
                if (progressBar != null) {
                    UIHelper.hideProgressBar(progressBar);
                }
            }

            @Override
            public void onFailure(Call<Question[]> call, Throwable t) {
                questionList[0] = null;
                Log.i("INFO", t.getMessage());
                if (progressBar != null) {
                    UIHelper.hideProgressBar(progressBar);
                }
            }
        });
        // imageView.clearAnimation();
        return questionList[0];
    }

}
