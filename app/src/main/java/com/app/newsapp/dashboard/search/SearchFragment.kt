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
import androidx.room.Room
import com.app.newsapp.R
import com.app.newsapp.common.BaseFragment
import com.app.newsapp.dashboard.Dashboard
import com.app.newsapp.dashboard.headlines.HeadlineAdapter
import com.app.newsapp.dashboard.headlines.HeadlineModel
import com.app.newsapp.dashboard.headlines.HeadlinesViewModel
import com.app.newsapp.database.category.CategoryDatabase
import com.app.newsapp.onboarding.category.CategoryModel
import com.app.newsapp.onboarding.category.HorizontalCategoryAdapter
import com.app.newsapp.utils.CategoryUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.LinkedList

@SuppressLint("NotifyDataSetChanged")
class SearchFragment : BaseFragment(), TextWatcher {

    private lateinit var headlinesAdapter: HeadlineAdapter
    private lateinit var categoryAdapter: HorizontalCategoryAdapter
    private lateinit var db: CategoryDatabase
    private lateinit var root: View
    private lateinit var noDataTv: TextView
    private lateinit var searchEt: EditText
    private var storedCategories = LinkedList<CategoryModel>()
    private var headlineModel = ArrayList<HeadlineModel>()
    private var selectedCategoryIndex = 0
    private lateinit var headlinesViewModel: HeadlinesViewModel
    private lateinit var observer: Observer<ArrayList<HeadlineModel>>
    private var oldSelectedIndex = 0
    private var uiInitialized = false
    private var searchWord=""
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

            val recycler = root.findViewById<RecyclerView>(R.id.recycler_categories)
            categoryAdapter = HorizontalCategoryAdapter(CategoryUtils.getAllCategories(), this)
            recycler.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
            recycler.adapter = categoryAdapter

            val recyclerHeadlines = root.findViewById<RecyclerView>(R.id.recycler_headlines)
            headlinesAdapter = HeadlineAdapter(headlineModel, this)
            recyclerHeadlines.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
            recyclerHeadlines.adapter = headlinesAdapter

            db = Room.databaseBuilder(
                requireContext(),
                CategoryDatabase::class.java, "CategoryTable"
            ).fallbackToDestructiveMigration().build()

            headlinesViewModel = HeadlinesViewModel()
            observer = Observer { list ->
                headlineModel.addAll(list)
                headlinesAdapter.notifyDataSetChanged()
                noDataTv.visibility = if (headlineModel.size == 0) VISIBLE else GONE
            }
            headlinesViewModel.getLiveData()?.observeForever(observer)

            getCategories()
            searchEt.addTextChangedListener(this)

        }
        return root
    }

    private fun getCategories() = runBlocking {
        withContext(Dispatchers.IO) {
            storedCategories.addAll(db.categoryDao().getAllCategories())
            storedCategories[1].selected = false
            storedCategories[2].selected = false
            categoryAdapter.notifyDataSetChanged()
            callHeadlines()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as Dashboard).showHide(false)
        (activity as Dashboard).setTitle(activity.getString(R.string.search))
    }

    private fun callHeadlines() {
        headlinesViewModel.start(storedCategories[selectedCategoryIndex].name,searchWord, requireContext())
    }

    fun onCategorySelected(position: Int) {
        if (oldSelectedIndex > -1 && oldSelectedIndex != position && storedCategories[oldSelectedIndex].selected) {
            storedCategories[oldSelectedIndex].selected = false
            categoryAdapter.notifyItemChanged(oldSelectedIndex)
        }
        categoryAdapter.notifyItemChanged(position)
        oldSelectedIndex = position
        if (oldSelectedIndex != selectedCategoryIndex) {
            selectedCategoryIndex = oldSelectedIndex
            headlineModel.clear()
            headlinesAdapter.notifyDataSetChanged()
            callHeadlines()
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

    }

    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        Handler(Looper.getMainLooper()).postDelayed({
            if(searchWord != searchEt.text.toString()){
                searchWord = searchEt.text.toString()
                callHeadlines()
            }
        }, 500)
    }

    override fun afterTextChanged(p0: Editable?) {

    }
}