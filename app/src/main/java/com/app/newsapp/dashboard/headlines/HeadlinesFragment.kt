package com.app.newsapp.dashboard.headlines

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.app.newsapp.R
import com.app.newsapp.common.HeadlinesBaseFragment
import com.app.newsapp.dashboard.Dashboard
import com.app.newsapp.onboarding.category.HorizontalCategoryAdapter

@SuppressLint("NotifyDataSetChanged")
class HeadlinesFragment : HeadlinesBaseFragment() {

    private lateinit var root: View
    private lateinit var swipeToRefresh: SwipeRefreshLayout
    private var uiInitialized = false
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!uiInitialized) {
            uiInitialized = true
            root = inflater.inflate(R.layout.fragment_headlines, container, false)

            noDataTv = root.findViewById(R.id.no_data_tv)
            swipeToRefresh = root.findViewById(R.id.swipeToRefresh)
            shimmerLoading = root.findViewById(R.id.loading_shimmer)

            val recycler = root.findViewById<RecyclerView>(R.id.recycler_categories)
            categoryAdapter = HorizontalCategoryAdapter(categories, this)
            recycler.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            recycler.adapter = categoryAdapter

            val recyclerHeadlines = root.findViewById<RecyclerView>(R.id.recycler_headlines)
            headlinesAdapter = HeadlineAdapter(headlineModels, this)
            recyclerHeadlines.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            recyclerHeadlines.adapter = headlinesAdapter


            getCategories()

            swipeToRefresh.setOnRefreshListener { // Your code to refresh the list here.
                swipeToRefresh.isRefreshing = false
                callHeadlines()
            }
        }
        return root
    }


    override fun onResume() {
        super.onResume()
        (activity as Dashboard).showHideSearch(true)
        (activity as Dashboard).showHideFav(true)
        (activity as Dashboard).setTitle(activity.getString(R.string.headlines))
    }

}