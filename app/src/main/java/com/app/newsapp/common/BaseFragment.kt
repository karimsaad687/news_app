package com.app.newsapp.common

import android.content.Context
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.app.newsapp.dashboard.Dashboard

open class BaseFragment:Fragment() {

    lateinit var activity: BaseActivity
    fun navigateTo(id:Int){
        Log.i("datadata",(requireView()==null).toString())
       // Navigation.findNavController().navigate(id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as BaseActivity)
        activity.setFragment(this)
    }
}