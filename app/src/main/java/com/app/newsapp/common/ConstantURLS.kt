package com.app.newsapp.common

class ConstantURLS {

    companion object {
        private val baseUrl = "https://newsapi.org/v2/"
        var headlines: String = baseUrl + "top-headlines"
    }
}