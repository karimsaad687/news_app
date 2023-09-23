package com.app.newsapp.utils

import com.app.newsapp.onboarding.category.CategoryModel
import java.util.LinkedList

class CategoryUtils {

    companion object {
        fun getAllCategories(): LinkedList<CategoryModel> {
            val list = LinkedList<CategoryModel>()
            list.addLast(CategoryModel("business", "عمل"))
            list.addLast(CategoryModel("entertainment", "ترفيه"))
            list.addLast(CategoryModel("general", "عام"))
            list.addLast(CategoryModel("health", "صحة"))
            list.addLast(CategoryModel("science", "علوم"))
            list.addLast(CategoryModel("sports", "رياضة"))
            list.addLast(CategoryModel("technology", "تكنولوجيا"))

            return list
        }
    }

}