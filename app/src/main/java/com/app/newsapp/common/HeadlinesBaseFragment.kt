package com.app.newsapp.common

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.app.newsapp.dashboard.headlines.HeadlineAdapter
import com.app.newsapp.dashboard.headlines.HeadlineModel
import com.app.newsapp.dashboard.headlines.HeadlinesViewModel
import com.app.newsapp.database.category.CategoryDatabase
import com.app.newsapp.database.headlines.HeadlineDatabase
import com.app.newsapp.onboarding.category.CategoryModel
import com.app.newsapp.onboarding.category.HorizontalCategoryAdapter
import com.facebook.shimmer.ShimmerFrameLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.LinkedList

@SuppressLint("NotifyDataSetChanged")
open class HeadlinesBaseFragment : BaseFragment() {

    private lateinit var categoryDB: CategoryDatabase
    private lateinit var headlinesDB: HeadlineDatabase
    private var selectedCategoryIndex = 0
    private var oldSelectedIndex = 0
    var categories = LinkedList<CategoryModel>()
    lateinit var categoryAdapter: HorizontalCategoryAdapter
    var headlineModels = ArrayList<HeadlineModel>()
    lateinit var headlinesAdapter: HeadlineAdapter
    lateinit var headlinesViewModel: HeadlinesViewModel
    private lateinit var observer: Observer<ArrayList<HeadlineModel>>
    lateinit var noDataTv: TextView
    lateinit var shimmerLoading: ShimmerFrameLayout
    var searchWord: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        categoryDB = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java, "CategoryTable"
        ).fallbackToDestructiveMigration().build()

        headlinesDB = Room.databaseBuilder(
            requireContext(),
            HeadlineDatabase::class.java, "HeadlineTable"
        ).fallbackToDestructiveMigration().build()

        headlinesViewModel = HeadlinesViewModel()
        observer = Observer { list ->
            Log.i("datadata","error "+list.size)
            headlineModels.addAll(list)
            showHideLoading(false)
            checkFavHeadlines()
            headlinesAdapter.notifyDataSetChanged()

            noDataTv.visibility = if (headlineModels.size == 0) RecyclerView.VISIBLE else View.GONE
        }
        headlinesViewModel.getLiveData()?.observeForever(observer)
    }

    override fun onResume() {
        super.onResume()
        if (headlineModels.size > 0) {
            Log.i("datadata", "hello")
            checkFavHeadlines()
        }
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
            headlineModels.clear()
            headlinesAdapter.notifyDataSetChanged()
            callHeadlines()
        }
    }

    private fun showHideLoading(flag: Boolean) {
        CoroutineScope(Dispatchers.Main).launch {
            if (flag) {
                noDataTv.visibility = View.GONE
                shimmerLoading.visibility = View.VISIBLE
                shimmerLoading.startShimmer()
            } else {
                shimmerLoading.visibility = View.GONE
                shimmerLoading.stopShimmer()
            }

        }
    }

    open fun callHeadlines() {
        headlineModels.clear()
        headlinesAdapter.notifyDataSetChanged()
        showHideLoading(true)
        headlinesViewModel.start(
            categories[selectedCategoryIndex].nameEn,
            searchWord,
            requireContext()
        )
    }

    open fun getStoredCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            categories.addAll(categoryDB.categoryDao().getAllCategories())
            categories[1].selected = false
            categories[2].selected = false
            categoryAdapter.notifyDataSetChanged()
            callHeadlines()
        }
    }

    open fun getAllAndStoredCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            val storedCategories =
                ArrayList<CategoryModel>(categoryDB.categoryDao().getAllCategories())
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

    open fun checkFavHeadlines() {
        CoroutineScope(Dispatchers.IO).launch {
            val headlineStoredModels =
                ArrayList<HeadlineModel>(headlinesDB.headlineDao().getAllHeadlines())
            for (index in 0 until headlineModels.size) {
                val storedIndex = headlineStoredModels.indexOf(headlineModels[index])
                if (storedIndex >= 0) {
                    headlineModels[index].isFav = true
                    headlineModels[index].id = headlineStoredModels[storedIndex].id
                } else {
                    headlineModels[index].isFav = false
                }
            }
            CoroutineScope(Dispatchers.Main).launch {
                headlinesAdapter.notifyDataSetChanged()
            }
            //
        }
    }

    open fun addRemoveHeadlineToFav(model: HeadlineModel, position: Int) = runBlocking {
        headlinesAdapter.notifyItemChanged(position)
        withContext(Dispatchers.IO) {
            if (model.isFav) {
                val id = headlinesDB.headlineDao().insertHeadline(model)
                model.id = id.toInt()
            } else {
                headlinesDB.headlineDao().deleteHeadline(model)
            }
        }
    }

    open fun getFavHeadline() = runBlocking {
        withContext(Dispatchers.IO) {
            headlineModels.addAll(headlinesDB.headlineDao().getAllHeadlines())
            CoroutineScope(Dispatchers.Main).launch {
                headlinesAdapter.notifyDataSetChanged()

                noDataTv.visibility = if (headlineModels.size == 0) RecyclerView.VISIBLE else View.GONE
            }
        }
    }

}