package com.app.newsapp.utils

import com.app.newsapp.onboarding.country.CountryModel
import java.util.LinkedList

class CountryUtils {

    companion object {
        fun getAllCountries(): LinkedList<CountryModel> {
            val list = LinkedList<CountryModel>()
            list.addLast(CountryModel("United States", "us"))
            list.addLast(CountryModel("Greece", "gr"))
            list.addLast(CountryModel("Netherlands", "nl"))
            list.addLast(CountryModel("South Africa", "za"))
            list.addLast(CountryModel("Egypt", "eg"))
            list.addLast(CountryModel("Cuba", "cu"))
            list.addLast(CountryModel("France", "fr"))
            list.addLast(CountryModel("Singapore", "sg"))
            list.addLast(CountryModel("UAE", "ae"))

            return list
        }
    }

}