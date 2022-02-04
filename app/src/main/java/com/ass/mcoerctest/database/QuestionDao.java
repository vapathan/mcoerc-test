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

    @Query("SELECT * FROM question WHERE subjectCode = :subjectCode AND chapterId = :chapterId ")
    List<Question> getQuestions(int subjectCode, int chapterId);

    @Query("SELECT id FROM question WHERE subjectCode = :subjectCode AND chapterId = :chapterId ")
    List<Integer> getQuestionIds(int subjectCode, int chapterId);

    @Query("SELECT * FROM question WHERE id = :questionId")
    Question getQuestion(int questionId);

    @Query("UPDATE question SET isVisited = 1 WHERE id = :questionId")
    void visitQuestion(int questionId);

    @Query("SELECT COUNT(*) as count from question where subjectCode = :subjectCode AND chapterId = :chapterId")
    int getChapterQuestionCount(int subjectCode, int chapterId);


}
