package com.app.newsapp.dashboard.headlines

import org.json.JSONObject

class HeadlineModel(json: JSONObject) {
    lateinit var sourceName: String
    var author: String
    var title: String
    var description: String
    var url: String
    var urlToImage: String
    var publishedAt: String

    init {
        if (json.has("source")) {
            sourceName = checkJson(json.getJSONObject("source"), "name")
        }
        author = checkJson(json, "author")
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