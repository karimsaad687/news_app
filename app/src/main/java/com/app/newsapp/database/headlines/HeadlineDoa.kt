package com.app.newsapp.database.headlines

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.newsapp.dashboard.headlines.HeadlineModel

@Dao
interface HeadlineDao {
    @Query("SELECT * FROM HeadlineTable")
    fun getAllHeadlines(): List<HeadlineModel>

    @Query("DELETE FROM HeadlineTable")
    fun deleteAllHeadlines()

    @Delete
    fun deleteHeadline(model: HeadlineModel): Int

    @Insert
    fun insertHeadline(model: HeadlineModel): Long
}