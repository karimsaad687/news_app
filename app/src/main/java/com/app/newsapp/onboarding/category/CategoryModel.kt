package com.app.newsapp.onboarding.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryTable")
data class CategoryModel constructor(
    @ColumnInfo(name = "name_en") val nameEn: String,
    @ColumnInfo(name = "name_ar") val nameAr: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0

    var selected: Boolean = false
}