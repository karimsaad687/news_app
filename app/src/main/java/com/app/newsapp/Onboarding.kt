package com.app.newsapp

import android.os.Bundle
import com.app.newsapp.common.BaseActivity

class Onboarding : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_onboarding)

    }

}