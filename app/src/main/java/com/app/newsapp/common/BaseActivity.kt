@file:Suppress("DEPRECATION")

package com.app.newsapp.common

import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
open class BaseActivity : AppCompatActivity() {
    lateinit var baseFragment: BaseFragment
    var isConnected = false
    var connectivityManager: ConnectivityManager? = null
    var activeNetworkInfo: NetworkInfo? = null

    // to check if we are monitoring Network
    private val connectivityCallback: NetworkCallback = object : NetworkCallback() {
        override fun onAvailable(network: Network) {
            isConnected = true
            connectionChanged(isConnected)
            Log.i("datadata", "INTERNET CONNECTED")
        }

        override fun onLost(network: Network) {
            isConnected = false
            activeNetworkInfo = connectivityManager!!.activeNetworkInfo
            isConnected = activeNetworkInfo != null && activeNetworkInfo!!.isConnectedOrConnecting
            connectionChanged(isConnected)
            Log.i("datadata", if (isConnected) "INTERNET CONNECTED" else "INTERNET LOST")
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

    open fun isConnected(): Boolean {
        return isConnected
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager = getSystemService(
            CONNECTIVITY_SERVICE
        ) as ConnectivityManager
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

}