package com.app.newsapp

import android.os.Bundle
import android.widget.TextView
import com.app.newsapp.common.BaseActivity

class Onboarding : BaseActivity() {

    private lateinit var langTv: TextView
    private lateinit var titleTv: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_onboarding)

        titleTv = findViewById(R.id.title_tv)
        langTv = findViewById(R.id.lang_tv)

        langTv.setOnClickListener {
            changeLang()
        }
    }

    fun setTitle(title: String) {
        titleTv.text=title
    }

}