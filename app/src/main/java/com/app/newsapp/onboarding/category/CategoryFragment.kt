package com.app.newsapp.onboarding.category

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.VERTICAL
import androidx.room.Room
import com.app.newsapp.R
import com.app.newsapp.common.BaseFragment
import com.app.newsapp.dashboard.Dashboard
import com.app.newsapp.database.category.CategoryDatabase
import com.app.newsapp.onboarding.Onboarding
import com.app.newsapp.utils.CategoryUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.LinkedList

class CategoryFragment : BaseFragment() {

    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var db: CategoryDatabase
    private lateinit var root: View
    private lateinit var confirmBtn: Button
    private var categorySelected = false
    private var numberOfSelectedItem = 0
    private lateinit var categories: LinkedList<CategoryModel>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        root = inflater.inflate(R.layout.fragment_category, container, false)

        confirmBtn = root.findViewById(R.id.confirm_btn)

        categories = CategoryUtils.getAllCategories()

        val recycler = root.findViewById<RecyclerView>(R.id.recycler)
        categoryAdapter = CategoryAdapter(categories, this)
        recycler.layoutManager = LinearLayoutManager(requireContext(), VERTICAL, false)
        recycler.adapter = categoryAdapter

        db = Room.databaseBuilder(
            requireContext(),
            CategoryDatabase::class.java, "CategoryTable"
        ).fallbackToDestructiveMigration().build()

        confirmBtn.setOnClickListener {
            if (categorySelected) {
                storeCategories()
            }
        }

        return root
    }

    override fun onResume() {
        super.onResume()
        (activity as Onboarding).setTitle(activity.getString(R.string.choose_category))
    }

    private fun storeCategories() {
        CoroutineScope(Dispatchers.IO).launch {
            for (index in 0 until categories.size) {
                if (categories[index].selected) {
                    db.categoryDao().insertCategory(categories[index])
                }
            }
            requireActivity().finish()
            startActivity(Intent(requireContext(), Dashboard::class.java))
        }
    }


    fun getNumberOfSelectedCategories(): Int {
        return numberOfSelectedItem
    }

    fun onCategorySelected(model: CategoryModel, position: Int) {
        categoryAdapter.notifyItemChanged(position)
        numberOfSelectedItem += if (model.selected) 1 else -1

        confirmBtn.alpha = if (numberOfSelectedItem == 3) 1.0f else 0.3f
        categorySelected = (numberOfSelectedItem == 3)

    }

}