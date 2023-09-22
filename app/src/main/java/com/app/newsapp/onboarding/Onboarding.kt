package com.app.newsapp.onboarding

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.app.newsapp.R
import com.app.newsapp.common.BaseActivity

class Onboarding : BaseActivity() {

    private lateinit var langTv: TextView
    private lateinit var titleTv: TextView
    private lateinit var backIm: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_onboarding)

        titleTv = findViewById(R.id.title_tv)
        langTv = findViewById(R.id.lang_tv)
        backIm = findViewById(R.id.back_im)

        langTv.setOnClickListener {
            changeLang()
        }

        backIm.setOnClickListener {
            onBackPressed()
        }

    }

    fun setTitle(title: String) {
        titleTv.text=title
    }

}