package com.ass.mcoerctest.repositories;

import android.content.Context;
import android.util.Log;

import com.ass.mcoerctest.constants.Api;
import com.ass.mcoerctest.models.Notification;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NotificationRepository {
    private static NotificationRepository mInstance;
    private RetrofitApi mRetrofitApi;


    public static NotificationRepository getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new NotificationRepository(context);
        }
        return mInstance;
    }

    private NotificationRepository(Context context) {
        RetrofitApiClient retrofitApiClient = RetrofitApiClient.getInstance();

        mRetrofitApi = retrofitApiClient.getRetrofitApi();

    }


    public List<Notification> getNotificationList() {
        //Get Notifications from remote server
        final List<Notification>[] notificationList = new List[]{new ArrayList<>()};

        Call<Notification[]> call = mRetrofitApi.getNotifications(Api.API_KEY);
        call.enqueue(new Callback<Notification[]>() {
            @Override
            public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                notificationList[0] = Arrays.asList(response.body());

            }

            @Override
            public void onFailure(Call<Notification[]> call, Throwable t) {
                notificationList[0] = null;
                Log.i("INFO", t.getMessage());
            }
        });
        return notificationList[0];
    }

}
