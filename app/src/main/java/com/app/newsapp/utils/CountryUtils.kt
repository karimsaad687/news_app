package com.app.newsapp.utils

import com.app.newsapp.onboarding.country.CountryModel
import java.util.LinkedList

class CountryUtils {

    companion object {
        fun getAllCountries(): LinkedList<CountryModel> {
            val list = LinkedList<CountryModel>()
            list.addLast(CountryModel("United States", "امريكا", "us"))
            list.addLast(CountryModel("Greece", "اليونان", "gr"))
            list.addLast(CountryModel("Netherlands", "هولندا", "nl"))
            list.addLast(CountryModel("South Africa", "جنوب أفريقيا", "za"))
            list.addLast(CountryModel("Egypt", "مصر", "eg"))
            list.addLast(CountryModel("Cuba", "كوبا", "cu"))
            list.addLast(CountryModel("France", "فرنسا", "fr"))
            list.addLast(CountryModel("Singapore", "سنغافورة", "sg"))
            list.addLast(CountryModel("UAE", "الإمارات العربية المتحدة", "ae"))

            return list
        }
    }

}