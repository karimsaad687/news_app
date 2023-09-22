package com.app.newsapp.dashboard.headlines

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.androidnetworking.error.ANError
import com.app.newsapp.common.ConstantURLS
import com.app.newsapp.networking.BaseViewModel
import org.json.JSONObject

class HeadlinesViewModel : BaseViewModel() {

    private var liveData: MutableLiveData<ArrayList<HeadlineModel>>? = null

    fun getLiveData(): MutableLiveData<ArrayList<HeadlineModel>>? {
        if (liveData == null) {
            liveData = MutableLiveData<ArrayList<HeadlineModel>>()
        }
        return liveData
    }

    override fun start(category: String, searchWord: String?, context: Context) {
        super.start(category, searchWord, context)
        get(ConstantURLS.headlines, category, searchWord, context)
    }

    override fun success(json: JSONObject) {
        super.success(json)
        val list = ArrayList<HeadlineModel>()
        if (json.has("articles")) {
            val articlesArray = json.getJSONArray("articles")
            for (i in 0 until articlesArray.length()) {
                val headLine = HeadlineModel(articlesArray.getJSONObject(i).toString())
                if (headLine.title != "[Removed]" && headLine.description != "[Removed]" && headLine.sourceName != "[Removed]")
                    list.add(headLine)
            }
        }
        if (list.size == 0) {
            getLiveData()?.postValue(list)
        } else {
            val sortedList = ArrayList<HeadlineModel>(list.sortedByDescending { it.publishedAt })

            getLiveData()?.postValue(sortedList)
        }
    }

    override fun failed(anError: ANError) {
        super.failed(anError)
        getLiveData()?.postValue(ArrayList())
    }


}