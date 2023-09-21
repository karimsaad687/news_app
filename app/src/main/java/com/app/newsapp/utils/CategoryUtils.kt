package com.app.newsapp.utils

import com.app.newsapp.onboarding.category.CategoryModel
import java.util.LinkedList

class CategoryUtils {

    companion object {
        fun getAllCategories(): LinkedList<CategoryModel> {
            val list = LinkedList<CategoryModel>()
            list.addLast(CategoryModel("business"))
            list.addLast(CategoryModel("entertainment"))
            list.addLast(CategoryModel("general"))
            list.addLast(CategoryModel("health"))
            list.addLast(CategoryModel("science"))
            list.addLast(CategoryModel("sports"))
            list.addLast(CategoryModel("technology"))

            return list
        }
    }

}