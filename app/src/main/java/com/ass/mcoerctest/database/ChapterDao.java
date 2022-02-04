package com.ass.mcoerctest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ass.mcoerctest.models.Chapter;

import java.util.List;

@Dao
public interface ChapterDao {
    @Insert
    void insert(Chapter chapter);

    @Query("SELECT * FROM chapter WHERE subjectCode = :subjectCode")
    List<Chapter> getChapters(int subjectCode);

    @Query("SELECT * FROM chapter WHERE id = :chapterId")
    Chapter getChapter(int chapterId);

}
