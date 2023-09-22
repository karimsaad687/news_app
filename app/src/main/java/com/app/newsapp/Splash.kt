package com.app.newsapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import androidx.room.Room
import com.app.newsapp.common.BaseActivity
import com.app.newsapp.dashboard.Dashboard
import com.app.newsapp.database.category.CategoryDatabase
import com.app.newsapp.onboarding.Onboarding
import com.app.newsapp.utils.AnimationUtils
import com.app.newsapp.utils.SharedPreferencesUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class Splash : BaseActivity() {

    private var isDataReady = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        hideNavigationBar()
        setContentView(R.layout.activity_splash)

        val logo = findViewById<ImageView>(R.id.logo_im)
        checkCountryAndCategories()
        AnimationUtils.showViewAnimation(logo)

        Handler(Looper.getMainLooper()).postDelayed({
            finish()
            startActivity(
                Intent(
                    this, if (isDataReady) Dashboard::class.java else Onboarding::class.java
                )
            )
        }, 2000)

    }

    private fun checkCountryAndCategories() = runBlocking<Unit> {
        launch(Dispatchers.IO) {
            val db = Room.databaseBuilder(
                applicationContext,
                CategoryDatabase::class.java, "CategoryTable"
            ).fallbackToDestructiveMigration().build()
            val numberOfCategories = db.categoryDao().getAllCategories().size

            isDataReady = !SharedPreferencesUtils.getCountry(this@Splash)
                .equals("") && numberOfCategories == 3

            if (numberOfCategories != 3) {
                db.categoryDao().deleteAllCategories()
            }

        }
    }
}