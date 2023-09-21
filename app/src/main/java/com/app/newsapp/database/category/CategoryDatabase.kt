package com.app.newsapp.database.category

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.newsapp.onboarding.category.CategoryModel

@Database(entities = [CategoryModel::class], version = 1)
abstract class CategoryDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
}