package com.app.newsapp.utils

import android.content.Context
import com.app.newsapp.R

class SharedPreferencesUtils {

    companion object {
        fun getCountry(context: Context): String? {
            val sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE
            )
            return sharedPreferences.getString(
                context.getString(R.string.app_name) + "_country", ""
            )
        }

        fun setCountry(context: Context, country: String?) {
            val editor = context.getSharedPreferences(
                context.getString(R.string.app_name), Context.MODE_PRIVATE
            ).edit()
            editor.putString(context.getString(R.string.app_name) + "_country", country)
            editor.apply()
        }
    }
}