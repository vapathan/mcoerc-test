package com.ass.mcoerctest.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.ass.mcoerctest.models.ScoreCard;

@Dao
public interface ScoreCardDao {

    @Insert
    void insert(ScoreCard scoreCard);

    @Query("SELECT * From scorecard where testId = :testId")
    ScoreCard getScoreCard(int testId);
}
