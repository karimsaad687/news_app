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
import androidx.room.Room
import com.app.newsapp.R
import com.app.newsapp.common.HeadlinesBaseFragment
import com.app.newsapp.dashboard.Dashboard
import com.app.newsapp.database.category.CategoryDatabase
import com.app.newsapp.onboarding.category.CategoryModel
import com.app.newsapp.onboarding.category.HorizontalCategoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.LinkedList

@SuppressLint("NotifyDataSetChanged")
class HeadlinesFragment : HeadlinesBaseFragment() {

    private lateinit var headlinesAdapter: HeadlineAdapter
    private lateinit var categoryAdapter: HorizontalCategoryAdapter
    private lateinit var db: CategoryDatabase
    private lateinit var root: View
    private lateinit var noDataTv: TextView
    private var categories = LinkedList<CategoryModel>()
    private var headlineModel = ArrayList<HeadlineModel>()
    private var selectedCategoryIndex = 0
    private lateinit var headlinesViewModel: HeadlinesViewModel
    private lateinit var observer: Observer<ArrayList<HeadlineModel>>
    private var oldSelectedIndex = 0
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
        }
        return root
    }

    private fun getCategories() = runBlocking {
        withContext(Dispatchers.IO) {
            categories.addAll(db.categoryDao().getAllCategories())
            categories[1].selected = false
            categories[2].selected = false
            categoryAdapter.notifyDataSetChanged()
            callHeadlines()
        }
    }

    override fun onResume() {
        super.onResume()
        (activity as Dashboard).showHide(true)
        (activity as Dashboard).setTitle(activity.getString(R.string.headlines))
    }

    private fun callHeadlines() {
        headlineModel.clear()
        headlinesAdapter.notifyDataSetChanged()
        headlinesViewModel.start(categories[selectedCategoryIndex].name, null, requireContext())
    }

    fun onCategorySelected(position: Int) {
        if (oldSelectedIndex > -1 && oldSelectedIndex != position && categories[oldSelectedIndex].selected) {
            categories[oldSelectedIndex].selected = false
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
}