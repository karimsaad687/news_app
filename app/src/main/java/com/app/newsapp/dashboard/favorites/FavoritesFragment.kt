package com.app.newsapp.dashboard.favorites

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import com.app.newsapp.R
import com.app.newsapp.common.HeadlinesBaseFragment
import com.app.newsapp.dashboard.Dashboard
import com.app.newsapp.dashboard.headlines.HeadlineAdapter

@SuppressLint("NotifyDataSetChanged")
class FavoritesFragment : HeadlinesBaseFragment() {

    private lateinit var root: View
    private var uiInitialized = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!uiInitialized) {
            uiInitialized = true
            root = inflater.inflate(R.layout.fragment_fav, container, false)

            noDataTv = root.findViewById(R.id.no_data_tv)

            val recycler = root.findViewById<RecyclerView>(R.id.recycler)
            headlinesAdapter = HeadlineAdapter(headlineModels, this)
            recycler.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            recycler.adapter = headlinesAdapter

            getFavHeadline()

        }
        return root
    }


    override fun onResume() {
        super.onResume()
        (activity as Dashboard).showHideSearch(false)
        (activity as Dashboard).showHideFav(false)
        (activity as Dashboard).setTitle(activity.getString(R.string.favorites))
    }

}