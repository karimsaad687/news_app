package com.app.newsapp.dashboard.headlines

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.json.JSONObject

@Entity(tableName = "HeadlineTable")
data class HeadlineModel constructor(val jsonString: String) {
    @ColumnInfo(name = "sourceName") lateinit var sourceName: String
    @ColumnInfo(name = "title") var title: String
    @ColumnInfo(name = "description") var description: String
    @ColumnInfo(name = "url") var url: String
    @ColumnInfo(name = "urlToImage") var urlToImage: String
    @ColumnInfo(name = "publishedAt") var publishedAt: String
    @ColumnInfo(name = "isFav") var isFav=false

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
    init {
        val json = JSONObject(jsonString)
        if (json.has("source")) {
            sourceName = checkJson(json.getJSONObject("source"), "name")
        }
        title = checkJson(json, "title")
        description = checkJson(json, "description")
        url = checkJson(json, "url")
        urlToImage = checkJson(json, "urlToImage")
        publishedAt = checkJson(json, "publishedAt")

    }

    private fun checkJson(json: JSONObject, jsonString: String): String {
        if (json.has(jsonString)) {
            return json.getString(jsonString)
        }
        return ""
    }
}