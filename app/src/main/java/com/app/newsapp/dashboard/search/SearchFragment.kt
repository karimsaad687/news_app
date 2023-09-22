package com.app.newsapp.dashboard.search

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.EditText
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
import com.app.newsapp.dashboard.headlines.HeadlineAdapter
import com.app.newsapp.dashboard.headlines.HeadlineModel
import com.app.newsapp.dashboard.headlines.HeadlinesViewModel
import com.app.newsapp.onboarding.category.HorizontalCategoryAdapter
import com.app.newsapp.utils.CategoryUtils

@SuppressLint("NotifyDataSetChanged")
class SearchFragment : HeadlinesBaseFragment(), TextWatcher {

    private lateinit var root: View
    private lateinit var noDataTv: TextView
    private lateinit var searchEt: EditText
    private lateinit var observer: Observer<ArrayList<HeadlineModel>>
    private var uiInitialized = false
    private var searchWord = ""
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!uiInitialized) {
            uiInitialized = true
            root = inflater.inflate(R.layout.fragment_search, container, false)

            noDataTv = root.findViewById(R.id.no_data_tv)
            searchEt = root.findViewById(R.id.search_et)

            categories = CategoryUtils.getAllCategories()

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


            getStoredCategories()
            searchEt.addTextChangedListener(this)

        }
        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as Dashboard).showHide(false)
        (activity as Dashboard).setTitle(activity.getString(R.string.search))
    }


    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            if (searchWord != searchEt.text.toString()) {
                searchWord = searchEt.text.toString()
                headlinesViewModel.cancel()
                callHeadlines()
            }
        }, 1000)
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}