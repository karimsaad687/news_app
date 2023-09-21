package com.app.newsapp.onboarding.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.app.newsapp.R
import java.util.LinkedList

class CategoryAdapter(
    private val list: LinkedList<CategoryModel>,
    private val categoryFragment: CategoryFragment
) :
    Adapter<CategoryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_category_item, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.onBind(list[position], position, categoryFragment)
    }

    class Holder(itemView: View) : ViewHolder(itemView) {

        private val categoryNameTv = itemView.findViewById<TextView>(R.id.category_tv)

        fun onBind(
            categoryModel: CategoryModel,
            position: Int,
            categoryFragment: CategoryFragment
        ) {
            categoryNameTv.text = categoryModel.name
            categoryNameTv.setTextColor(
                categoryFragment.requireContext()
                    .getColor(if (categoryModel.selected) R.color.green else R.color.black)
            )
            itemView.setBackgroundResource(
                if (categoryModel.selected) R.drawable.country_item_border else R.drawable.country_item
            )
            itemView.setOnClickListener {
                if(categoryFragment.getNumberOfSelectedCategories()<3 || (categoryFragment.getNumberOfSelectedCategories()==3 && categoryModel.selected)) {
                    categoryModel.selected = !categoryModel.selected
                    categoryFragment.onCategorySelected(categoryModel, position)
                }
            }
        }
    }
}