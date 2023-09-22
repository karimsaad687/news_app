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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        titleTv = findViewById(R.id.title_tv)
        searchIm = findViewById(R.id.search_im)

        searchIm.setOnClickListener {
            baseFragment.navigateTo(R.id.searchFragment)
        }
    }

    fun showHide(show: Boolean) {
        searchIm.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setTitle(title: String) {
        titleTv.text=title
    }
}