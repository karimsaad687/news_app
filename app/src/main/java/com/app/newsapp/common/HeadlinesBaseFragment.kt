package com.app.newsapp.common

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.room.Room
import com.app.newsapp.dashboard.headlines.HeadlineAdapter
import com.app.newsapp.dashboard.headlines.HeadlineModel
import com.app.newsapp.dashboard.headlines.HeadlinesViewModel
import com.app.newsapp.database.category.CategoryDatabase
import com.app.newsapp.onboarding.category.CategoryModel
import com.app.newsapp.onboarding.category.HorizontalCategoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.LinkedList

@SuppressLint("NotifyDataSetChanged")
open class HeadlinesBaseFragment : BaseFragment() {

    private lateinit var db: CategoryDatabase
    private var selectedCategoryIndex = 0
    private var oldSelectedIndex = 0
    var categories = LinkedList<CategoryModel>()
    lateinit var categoryAdapter: HorizontalCategoryAdapter
    var headlineModel = ArrayList<HeadlineModel>()
    lateinit var headlinesAdapter: HeadlineAdapter
    lateinit var headlinesViewModel: HeadlinesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java, "CategoryTable"
        ).fallbackToDestructiveMigration().build()
    }
    open fun onCategorySelected(position: Int) {
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

    open fun callHeadlines() {
        headlineModel.clear()
        headlinesAdapter.notifyDataSetChanged()
        headlinesViewModel.start(categories[selectedCategoryIndex].name, null, requireContext())
    }

    open fun getCategories() = runBlocking {
        withContext(Dispatchers.IO) {
            categories.addAll(db.categoryDao().getAllCategories())
            categories[1].selected = false
            categories[2].selected = false
            categoryAdapter.notifyDataSetChanged()
            callHeadlines()
        }
    }

    open fun getStoredCategories() = runBlocking {
        withContext(Dispatchers.IO) {
            val storedCategories = ArrayList<CategoryModel>(db.categoryDao().getAllCategories())
            for (index in storedCategories.size - 1 downTo 0) {
                categories.remove(storedCategories[index])
                categories.addFirst(storedCategories[index])
            }
            categories[1].selected = false
            categories[2].selected = false
            categoryAdapter.notifyDataSetChanged()
            callHeadlines()
        }
    }

}