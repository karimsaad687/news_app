@file:Suppress("DEPRECATION")

package com.app.newsapp.common

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.app.newsapp.utils.SharedPreferencesUtils
import java.util.Locale

open class BaseActivity : AppCompatActivity() {
    lateinit var baseFragment: BaseFragment
    var isConnected = false
    var connectivityManager: ConnectivityManager? = null
    var activeNetworkInfo: NetworkInfo? = null
    lateinit var lang: String

    // to check if we are monitoring Network
    private val connectivityCallback: NetworkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            isConnected = true
            connectionChanged(true)
        }

        override fun onLost(network: Network) {
            isConnected = false
            activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            isConnected = activeNetworkInfo != null && activeNetworkInfo!!.isConnectedOrConnecting
            connectionChanged(isConnected)
        }
    }

    open fun connectionChanged(state: Boolean) {}

    // here we are getting the connectivity service from connectivity manager
    private fun checkConnectivity() {
        connectivityManager!!.registerNetworkCallback(
            NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build(), connectivityCallback
        )
    }

    override fun onResume() {
        super.onResume()
        checkConnectivity()
    }

    open fun isNetworkConnected(): Boolean {
        return isConnected
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager = getSystemService(
            CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        lang = SharedPreferencesUtils.getLang(this).toString()
        if (lang == "") {
            lang = Locale.getDefault().language
        }
        setLang()
    }

    fun hideStatusBar() {
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun hideNavigationBar() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    fun setFragment(baseFragment: BaseFragment) {
        this.baseFragment = baseFragment
    }

    open fun changeLang() {

        lang = if (lang == "en") "ar" else "en"
        SharedPreferencesUtils.setLang(this, lang)
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = baseContext.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        window.decorView.layoutDirection =
            if (lang == "en") View.LAYOUT_DIRECTION_LTR else View.LAYOUT_DIRECTION_RTL
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )

        recreate()
    }

    open fun setLang() {
        SharedPreferencesUtils.setLang(this, lang)
        val locale = Locale(lang)
        Locale.setDefault(locale)
        val config = baseContext.resources.configuration
        config.setLocale(locale)
        config.setLayoutDirection(locale)
        window.decorView.layoutDirection =
            if (lang == "en") View.LAYOUT_DIRECTION_LTR else View.LAYOUT_DIRECTION_RTL
        baseContext.resources.updateConfiguration(
            config,
            baseContext.resources.displayMetrics
        )
    }

}