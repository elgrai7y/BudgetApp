package com.depi.budgetapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.depi.budgetapp.data.Category
import com.depi.budgetapp.data.CategoryDao
import com.depi.budgetapp.data.Transaction

class CategoryRepository(private val categoryDao: CategoryDao) {

    val allCategories: LiveData<List<Category>> =
        categoryDao.getAllCAtegory().asLiveData()


    suspend fun insert(category: Category) {
        categoryDao.insertCategory(category)
    }


    fun getIncomeCategory() = categoryDao.getIncomeCategory().asLiveData()

    fun getExpenseCategory() = categoryDao.getExpenseCategory().asLiveData()

}