package com.app.newsapp.networking

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.app.newsapp.BuildConfig
import com.app.newsapp.MyApplication
import com.app.newsapp.utils.SharedPreferencesUtils
import org.json.JSONException
import org.json.JSONObject

open class Networking : ViewModel() {

    open fun get(url:String,category:String,context:Context){
        val map=HashMap<String,String>()
        map.put("category",category)
        map.put("apiKey",BuildConfig.app_key)
        map.put("country", SharedPreferencesUtils.getCountry(context).toString())
        AndroidNetworking.get(url)
            .setPriority(Priority.MEDIUM)
            .addQueryParameter(map)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(jsonObject: JSONObject) {
                    Log.i("datadata",jsonObject.toString())
                    success(jsonObject)
                }

                override fun onError(anError: ANError) {
                    Log.i("datadata",anError.errorDetail.toString())
                }
            })
    }

    open fun start(category:String,context:Context){

    }

    open fun success(json:JSONObject){

    }
}