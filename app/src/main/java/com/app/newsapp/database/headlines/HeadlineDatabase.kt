package com.app.newsapp.database.headlines

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.newsapp.dashboard.headlines.HeadlineModel

@Database(entities = [HeadlineModel::class], version = 1)
abstract class HeadlineDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
}