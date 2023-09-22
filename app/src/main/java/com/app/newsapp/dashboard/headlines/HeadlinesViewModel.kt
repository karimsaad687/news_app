package com.app.newsapp.dashboard.headlines

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.newsapp.common.ConstantURLS
import com.app.newsapp.networking.Networking
import org.json.JSONObject
import java.util.LinkedList

class HeadlinesViewModel : Networking() {

    private var liveData: MutableLiveData<LinkedList<HeadlineModel>>? = null

    fun getLiveData(): MutableLiveData<LinkedList<HeadlineModel>>? {
        if (liveData == null) {
            liveData = MutableLiveData<LinkedList<HeadlineModel>>()
        }
        return liveData
    }

    override fun start(category: String, context: Context) {
        super.start(category, context)
        get(ConstantURLS.headlines, category, context)
    }

    override fun success(json: JSONObject) {
        super.success(json)
        val list = LinkedList<HeadlineModel>()
        if (json.has("articles")) {
            val articlesArray = json.getJSONArray("articles")
            for (i in 0 until articlesArray.length()) {
                list.addLast(HeadlineModel(articlesArray.getJSONObject(i)))
            }
        }
        Log.i("datadata2",(getLiveData()==null).toString())
        getLiveData()?.postValue(list)
    }
}