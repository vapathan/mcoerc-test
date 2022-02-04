package com.ass.mcoerctest.services;

import com.ass.mcoerctest.constants.Api;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitApiClient {
    public static final String BASE_URL = Api.SERVER_URL;
    private static RetrofitApiClient mInstance;
    private Retrofit retrofit;

    private RetrofitApiClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS).build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    public static RetrofitApiClient getInstance() {
        if (mInstance == null) {
            mInstance = new RetrofitApiClient();
        }

        return mInstance;
    }

    public RetrofitApi getRetrofitApi() {
        return retrofit.create(RetrofitApi.class);
    }
}
