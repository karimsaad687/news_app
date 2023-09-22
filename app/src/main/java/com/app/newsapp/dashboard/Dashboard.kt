package com.app.newsapp.dashboard

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.app.newsapp.R
import com.app.newsapp.common.BaseActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class Dashboard : BaseActivity() {

    private lateinit var titleTv: TextView
    private lateinit var searchIm: ImageView
    private lateinit var favIm: ImageView
    private lateinit var wifiIm: ImageView
    private lateinit var langTv: TextView
    private lateinit var backIm: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        titleTv = findViewById(R.id.title_tv)
        searchIm = findViewById(R.id.search_im)
        favIm = findViewById(R.id.fav_im)
        wifiIm = findViewById(R.id.wifi_im)
        langTv = findViewById(R.id.lang_tv)
        backIm = findViewById(R.id.back_im)

        searchIm.setOnClickListener {
            baseFragment.navigateTo(R.id.searchFragment)
        }

        favIm.setOnClickListener {
            baseFragment.navigateTo(R.id.favoritesFragment)
        }

        langTv.setOnClickListener {
            changeLang()
        }

        backIm.setOnClickListener {
            onBackPressed()
        }

    }

    fun showHideSearch(show: Boolean) {
        searchIm.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showHideFav(show: Boolean) {
        favIm.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun showHideBack(show: Boolean) {
        backIm.visibility = if (show) View.VISIBLE else View.GONE
    }

    fun setTitle(title: String) {
        titleTv.text=title
    }

    override fun connectionChanged(state: Boolean) {
        super.connectionChanged(state)
        CoroutineScope(Dispatchers.Main).launch {
            wifiIm.visibility = if (state) View.GONE else View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        wifiIm.visibility = if(isNetworkConnected()) View.GONE else View.VISIBLE
    }
}