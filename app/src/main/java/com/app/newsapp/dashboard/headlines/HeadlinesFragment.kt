package com.app.newsapp.dashboard.headlines

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.recyclerview.widget.RecyclerView.VISIBLE
import com.app.newsapp.R
import com.app.newsapp.common.HeadlinesBaseFragment
import com.app.newsapp.dashboard.Dashboard
import com.app.newsapp.onboarding.category.HorizontalCategoryAdapter

@SuppressLint("NotifyDataSetChanged")
class HeadlinesFragment : HeadlinesBaseFragment() {

    private lateinit var root: View
    private lateinit var noDataTv: TextView
    private lateinit var observer: Observer<ArrayList<HeadlineModel>>
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

            val recycler = root.findViewById<RecyclerView>(R.id.recycler_categories)
            categoryAdapter = HorizontalCategoryAdapter(categories, this)
            recycler.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            recycler.adapter = categoryAdapter

            val recyclerHeadlines = root.findViewById<RecyclerView>(R.id.recycler_headlines)
            headlinesAdapter = HeadlineAdapter(headlineModel, this)
            recyclerHeadlines.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            recyclerHeadlines.adapter = headlinesAdapter

            headlinesViewModel = HeadlinesViewModel()
            observer = Observer { list ->
                headlineModel.addAll(list)
                headlinesAdapter.notifyDataSetChanged()
                noDataTv.visibility = if (headlineModel.size == 0) VISIBLE else GONE
            }
            headlinesViewModel.getLiveData()?.observeForever(observer)

            getCategories()
        }
        return root
    }


    override fun onResume() {
        super.onResume()
        (activity as Dashboard).showHide(true)
        (activity as Dashboard).setTitle(activity.getString(R.string.headlines))
    }

}