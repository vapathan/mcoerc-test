package com.ass.mcoerctest.repositories;

import android.content.Context;

import com.ass.mcoerctest.database.AppDatabase;
import com.ass.mcoerctest.models.ScoreCard;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;
import com.ass.mcoerctest.utilities.AppExecutor;

public class ScoreCardRepository {
    private static ScoreCardRepository mInstance;
    private RetrofitApi mRetrofitApi;
    private AppDatabase mDb;
    private Context mContext;

    public static ScoreCardRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ScoreCardRepository(context);
        }
        return mInstance;
    }

    private ScoreCardRepository(Context context) {
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


    public ScoreCard getScoreCard(int testId) {
        return mDb.scoreCardDao().getScoreCard(testId);
    }

    public void saveScoreCard(final ScoreCard scoreCard) {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if (!isTestExists(scoreCard.getId())) {
                    mDb.scoreCardDao().insert(scoreCard);
                }
            }
        });
    }


}
