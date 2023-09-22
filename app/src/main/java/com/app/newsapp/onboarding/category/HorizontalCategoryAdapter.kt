package com.app.newsapp.onboarding.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.newsapp.R
import com.app.newsapp.common.BaseFragment
import com.app.newsapp.dashboard.headlines.HeadlinesFragment
import com.app.newsapp.dashboard.search.SearchFragment
import java.util.LinkedList

class HorizontalCategoryAdapter(
    private val list: LinkedList<CategoryModel>,
    private val baseFragment: BaseFragment
) :
    Adapter<HorizontalCategoryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_horizontal_category_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(list[position], position, baseFragment)
    }

    class Holder(itemView: View) : ViewHolder(itemView) {

        private val categoryNameTv = itemView.findViewById<TextView>(R.id.category_tv)

        fun onBind(
            categoryModel: CategoryModel,
            position: Int,
            baseFragment: BaseFragment
        ) {
            categoryNameTv.text = categoryModel.name
            categoryNameTv.setTextColor(
                baseFragment.requireContext()
                    .getColor(if (categoryModel.selected) R.color.green else R.color.black)
            )
            itemView.setBackgroundResource(
                if (categoryModel.selected) R.drawable.country_item_border else R.drawable.country_item
            )
            itemView.setOnClickListener {
                categoryModel.selected = true
                if (baseFragment is HeadlinesFragment) {
                    baseFragment.onCategorySelected(position)
                }else if (baseFragment is SearchFragment) {
                    baseFragment.onCategorySelected(position)
                }
            }
        }
    }
}