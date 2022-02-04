package com.ass.mcoerctest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ass.mcoerctest.models.Subject;

import java.util.List;

@Dao
public interface SubjectDao {
    @Insert
    void insert(Subject subject);

    @Query("SELECT * FROM subject WHERE code = :subjectCode")
    Subject getSubject(int subjectCode);

    @Query("SELECT * FROM subject")
    List<Subject> getSubjects();

}
