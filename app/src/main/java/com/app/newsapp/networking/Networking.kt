package com.app.newsapp.networking

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.app.newsapp.BuildConfig
import com.app.newsapp.utils.SharedPreferencesUtils
import org.json.JSONObject

open class Networking : ViewModel() {

    open fun get(url: String, category: String,searchWord: String? = null, context: Context) {
        val map = HashMap<String, String>()
        map["category"] = category
        map["apiKey"] = BuildConfig.app_key
        map["country"] = SharedPreferencesUtils.getCountry(context).toString()
        if (searchWord != null) {
            map["q"] = searchWord
        }
        Log.i("datadata",map.toString()+"")
        AndroidNetworking.get(url)
            .setPriority(Priority.MEDIUM)
            .addQueryParameter(map)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(jsonObject: JSONObject) {
                    Log.i("datadata", jsonObject.toString())
                    success(jsonObject)
                }

                override fun onError(anError: ANError) {
                    failed(anError)
                    Log.i("datadata2", anError.errorBody.toString())
                }
            })
    }

    open fun cancel(){
        AndroidNetworking.cancelAll()
    }

    open fun start(category: String, searchWord: String? = null, context: Context) {

    }

    open fun success(json: JSONObject) {

    }

    open fun failed(anError: ANError) {

    }
}