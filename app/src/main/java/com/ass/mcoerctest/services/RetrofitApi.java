package com.ass.mcoerctest.services;


import com.ass.mcoerctest.models.Chapter;
import com.ass.mcoerctest.models.Notification;
import com.ass.mcoerctest.models.Question;
import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.models.Subject;
import com.ass.mcoerctest.models.Test;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitApi {

    @GET("subject")
    Call<Subject[]> getSubjects(@Query("apiKey") String apiKey);

    @GET("chapter")
    Call<Chapter[]> getChapters(@Query("apiKey") String apiKey);

    @GET("chapter")
    Call<Chapter[]> getChapters(@Query("apiKey") String apiKey, @Query("subjectCode") int subjectCode);

    @GET("get-test-paper-questions")
    Call<Question[]> getQuestions(@Query("apiKey") String apiKey, @Query("testId") int testId);

    @FormUrlEncoded
    @POST("student/register")
    Call<ApiResponse> registerStudent(@Field("apiKey") String apiKey, @Field("studentDetails") String userString);

    @GET("student/getStudentByMobile")
    Call<Student> getStudentByMobile(@Query("apiKey") String apiKey, @Query("mobile") String mobile);

    @GET("tests-tobe-taken-by-student")
    Call<Test[]> getTests(@Query("apiKey") String apiKey, @Query("prn") String prn);

    @GET("getTestQuestions")
    Call<Question[]> getTestQuestions(@Query("apiKey") String apiKey, @Query("testId") int testId);

    @GET("notification")
    Call<Notification[]> getNotifications(@Query("apiKey") String apiKey);

    @FormUrlEncoded
    @POST("test/saveScoreCard")
    Call<ApiResponse> saveScoreCard(@Field("apiKey") String apiKey, @Field("scoreCard") String scoreCardString);

    @FormUrlEncoded
    @POST("test/save-responses")
    Call<ApiResponse> saveResponses(@Field("apiKey") String apiKey, @Field("responses") String responses);

}
