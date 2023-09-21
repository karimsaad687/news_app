package com.app.newsapp.database.category

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.app.newsapp.onboarding.category.CategoryModel

@Dao
interface CategoryDao {
    @Query("SELECT * FROM CategoryTable")
    fun getAllCategories(): List<CategoryModel>

    @Query("DELETE FROM CategoryTable")
    fun deleteAllCategories()

    @Delete
    fun deleteCategory(model: CategoryModel): Int

    @Insert
    fun insertCategory(model: CategoryModel): Long
}