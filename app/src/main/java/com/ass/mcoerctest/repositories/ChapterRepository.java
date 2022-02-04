package com.ass.mcoerctest.repositories;

import android.content.Context;
import android.util.Log;

import com.ass.mcoerctest.constants.Api;
import com.ass.mcoerctest.database.AppDatabase;
import com.ass.mcoerctest.models.Chapter;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;
import com.ass.mcoerctest.utilities.AppExecutor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChapterRepository {
    private static ChapterRepository mInstance;
    private RetrofitApi mRetrofitApi;
    private AppDatabase mDb;

    public static ChapterRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new ChapterRepository(context);
        }
        return mInstance;
    }

    private ChapterRepository(Context context) {
        RetrofitApiClient retrofitApiClient = RetrofitApiClient.getInstance();

        mRetrofitApi = retrofitApiClient.getRetrofitApi();
        mDb = AppDatabase.getInstance(context);
    }


    private boolean isChapterExists(int chapterId) {
        if (mDb.chapterDao().getChapter(chapterId) != null) {
            return true;
        } else {
            return false;
        }
    }

    public void saveChapter(final Chapter chapter) {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                if (!isChapterExists(chapter.getId())) {
                    mDb.chapterDao().insert(chapter);
                }
            }
        });
    }

    public void saveChapters(final List<Chapter> chapterList) {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                for (Chapter chapter : chapterList) {
                    saveChapter(chapter);
                }
            }
        });

    }


    public List<Chapter> getChapters(int subjectCode) {
        return mDb.chapterDao().getChapters(subjectCode);
    }


    public List<Chapter> getChapterList() {
        //Get Chapters data from remote server
        final List<Chapter>[] chapterList = new List[]{new ArrayList<>()};

        Call<Chapter[]> call = mRetrofitApi.getChapters(Api.API_KEY);
        call.enqueue(new Callback<Chapter[]>() {
            @Override
            public void onResponse(Call<Chapter[]> call, Response<Chapter[]> response) {
                chapterList[0] = Arrays.asList(response.body());
                Log.i("INFO", "TTT : " + chapterList[0].toString());
                saveChapters(chapterList[0]);
            }

            @Override
            public void onFailure(Call<Chapter[]> call, Throwable t) {
                chapterList[0] = null;
                Log.i("INFO", t.getMessage());
            }
        });
        return chapterList[0];
    }

}
