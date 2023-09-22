package com.app.newsapp.common

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

open class BaseFragment : Fragment() {

    lateinit var activity: BaseActivity
    fun navigateTo(id: Int) {
        Navigation.findNavController(requireView()).navigate(id)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = (context as BaseActivity)
    }

    override fun onResume() {
        super.onResume()
        activity.setFragment(this)
    }
}