package com.app.newsapp.onboarding.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CategoryTable")
data class CategoryModel constructor(
    @ColumnInfo(name = "name") val name: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0 // or foodId: Int? = null

    var selected:Boolean=false
}