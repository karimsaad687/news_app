package com.app.newsapp.common

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation

open class BaseFragment:Fragment() {



    fun navigateTo(id:Int){
        Navigation.findNavController(requireView()).navigate(id)
    }
}