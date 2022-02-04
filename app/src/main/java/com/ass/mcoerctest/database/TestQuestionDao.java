package com.ass.mcoerctest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ass.mcoerctest.models.TestQuestion;

import java.util.List;

@Dao
public interface TestQuestionDao {

    @Insert
    void insert(TestQuestion testQuestion);

    @Query("SELECT * FROM testquestion WHERE id = :id AND questionId = :questionId")
    TestQuestion getTestQuestion(int id, int questionId);

    @Query("SELECT * FROM testquestion WHERE testId = :testId ORDER BY subjectCode, questionId")
    List<TestQuestion> getTestQuestions(int testId);

    @Query("SELECT questionId FROM testquestion WHERE testId = :testId")
    List<Integer> getTestQuestionsIds(int testId);

    @Query("UPDATE testquestion set isAttempted = 1 , selectedOption = :selectedOption WHERE testId = :testId AND questionId = :questionId ")
    void markTestQuestionAsAttempted(int testId, int questionId, String selectedOption);
}
