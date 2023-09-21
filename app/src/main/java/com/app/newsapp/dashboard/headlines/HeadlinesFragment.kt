package com.app.newsapp.dashboard.headlines

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.HORIZONTAL
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

class HeadlinesFragment : BaseFragment() {

    private lateinit var categoryAdapter: HorizontalCategoryAdapter
    private lateinit var db: CategoryDatabase
    private lateinit var root: View
    private var storedCategories = LinkedList<CategoryModel>()
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

        db = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java, "CategoryTable"
        ).fallbackToDestructiveMigration().build()

        getCategories()
        return root
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getCategories() = runBlocking {
        withContext(Dispatchers.IO) {
            storedCategories.addAll(db.categoryDao().getAllCategories())
            categoryAdapter.notifyDataSetChanged()
        }
    }
}