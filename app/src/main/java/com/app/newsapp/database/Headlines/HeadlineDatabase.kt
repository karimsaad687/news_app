package com.app.newsapp.database.Headlines

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.newsapp.dashboard.headlines.HeadlineModel
import com.app.newsapp.onboarding.category.CategoryModel

@Database(entities = [HeadlineModel::class], version = 1)
abstract class HeadlineDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
}