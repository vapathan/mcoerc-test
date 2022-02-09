package com.ass.mcoerctest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ass.mcoerctest.models.Question;

import java.util.List;

@Dao
public interface QuestionDao {
    @Insert
    void insert(Question question);

    @Query("SELECT * FROM question WHERE testId = :testId ")
    List<Question> getQuestions(int testId);

    @Query("SELECT id FROM question WHERE courseCode = :subjectCode AND unitId = :chapterId ")
    List<Integer> getQuestionIds(int subjectCode, int chapterId);

    @Query("SELECT * FROM question WHERE id = :questionId")
    Question getQuestion(int questionId);

    @Query("UPDATE question SET isVisited = 1 WHERE id = :questionId")
    void visitQuestion(int questionId);

    @Query("SELECT COUNT(*) as count from question where courseCode = :subjectCode AND unitId = :chapterId")
    int getChapterQuestionCount(int subjectCode, int chapterId);

    @Query("DELETE from question where testId = :testId")
    void deleteTestQuestions(int testId);

    @Query("UPDATE question set isAttempted = 1 , selectedOption = :selectedOption WHERE testId = :testId AND id = :questionId ")
    void markTestQuestionAsAttempted(int testId, int questionId, String selectedOption);
}
