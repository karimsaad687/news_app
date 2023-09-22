package com.app.newsapp.dashboard.headlines

import android.annotation.SuppressLint
import android.database.Observable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.room.Room
import com.app.newsapp.R
import com.app.newsapp.common.BaseFragment
import com.app.newsapp.database.category.CategoryDatabase
import com.app.newsapp.onboarding.category.CategoryModel
import com.app.newsapp.onboarding.category.HorizontalCategoryAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.LinkedList

@SuppressLint("NotifyDataSetChanged")
class HeadlinesFragment : BaseFragment() {

    private lateinit var categoryAdapter: HorizontalCategoryAdapter
    private lateinit var db: CategoryDatabase
    private lateinit var root: View
    private var storedCategories = LinkedList<CategoryModel>()
    private var headlineModel = LinkedList<HeadlineModel>()
    private var selectedCategoryIndex=0
    private lateinit var headlinesViewModel:HeadlinesViewModel
    private lateinit var observer:Observer<LinkedList<HeadlineModel>>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_headlines, container, false)

        val recycler = root.findViewById<RecyclerView>(R.id.recycler_categories)
        categoryAdapter = HorizontalCategoryAdapter(storedCategories, this)
        recycler.layoutManager = LinearLayoutManager(requireContext(), HORIZONTAL, false)
        recycler.adapter = categoryAdapter

        val recyclerHeadlines = root.findViewById<RecyclerView>(R.id.recycler_headlines)
        val headlinesAdapter = HeadlineAdapter(headlineModel, this)
        recyclerHeadlines.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        recyclerHeadlines.adapter = headlinesAdapter

        db = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java, "CategoryTable"
        ).fallbackToDestructiveMigration().build()

        getCategories()

        headlinesViewModel= HeadlinesViewModel()
        observer= Observer {
            fun onChanged(list: LinkedList<HeadlineModel>) {
                headlineModel.addAll(list)
                headlinesAdapter.notifyDataSetChanged()
                Log.i("datadata","headlines")
            }
        }
        headlinesViewModel.getLiveData()?.observe(viewLifecycleOwner,observer)

        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getCategories() = runBlocking {
        withContext(Dispatchers.IO) {
            storedCategories.addAll(db.categoryDao().getAllCategories())
            storedCategories[1].selected=false
            storedCategories[2].selected=false
            categoryAdapter.notifyDataSetChanged()
            callHeadlines()
        }
    }

    private fun callHeadlines(){
        HeadlinesViewModel().start(storedCategories[selectedCategoryIndex].name,requireContext())
    }
}