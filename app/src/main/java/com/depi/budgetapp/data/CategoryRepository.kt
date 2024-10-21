package com.depi.budgetapp.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: Flow<List<Category>> = categoryDao.getAllCAtegory()

    suspend fun insertCategory(category: Category) {
        categoryDao.insertCategory(category)
    }

}
