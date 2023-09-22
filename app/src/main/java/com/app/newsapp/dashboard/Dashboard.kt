package com.app.newsapp.dashboard

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.app.newsapp.R
import com.app.newsapp.common.BaseActivity

class Dashboard : BaseActivity() {

    private lateinit var titleTv: TextView
    private lateinit var searchIm: ImageView
    private lateinit var favIm: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        titleTv = findViewById(R.id.title_tv)
        searchIm = findViewById(R.id.search_im)
        favIm = findViewById(R.id.fav_im)

        searchIm.setOnClickListener {
            baseFragment.navigateTo(R.id.searchFragment)
        }

        favIm.setOnClickListener {
            baseFragment.navigateTo(R.id.favoritesFragment)
        }
    }

    fun showHideSearch(show: Boolean) {
        searchIm.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showHideFav(show: Boolean) {
        favIm.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setTitle(title: String) {
        titleTv.text=title
    }
}