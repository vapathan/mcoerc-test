package com.ass.mcoerctest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ass.mcoerctest.models.Question;
import com.ass.mcoerctest.models.Test;

import java.util.List;

@Dao
public interface TestDao {

    @Insert
    void insert(Test test);

    @Query("SELECT * FROM TEST WHERE id = :testId")
    Test getTest(int testId);

    @Query("SELECT * FROM TEST")
    List<Test> getTests();


    @Query("SELECT * FROM question WHERE id IN (SELECT questionId from testquestion WHERE testId = :testId ORDER BY questionId)")
    List<Question> getQuestions(int testId);




}
