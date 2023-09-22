package com.app.newsapp.utils

import android.annotation.SuppressLint
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateUtils {

    companion object {
        @SuppressLint("SimpleDateFormat")
        fun convertIsoFormatToLocalTime(stringDate: String):String {
            val isoUtcFormat: DateFormat =
                SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            isoUtcFormat.timeZone = TimeZone.getTimeZone("UTC")
            val date = isoUtcFormat.parse(stringDate)

            val dateFormat: DateFormat = SimpleDateFormat("dd/mm/yyyy hh:mm a")
            return dateFormat.format(date as Date)
        }
    }
}