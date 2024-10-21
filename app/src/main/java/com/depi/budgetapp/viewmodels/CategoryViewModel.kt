package com.depi.budgetapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.depi.budgetapp.data.Category
import com.depi.budgetapp.data.CategoryDao
import com.depi.budgetapp.data.CategoryDatabase
import com.depi.budgetapp.data.Transaction
import com.depi.budgetapp.data.TransactionDatabase
import com.depi.budgetapp.repo.TransactionRepository
import kotlinx.coroutines.launch
import com.depi.budgetapp.repo.CategoryRepository

class CategoryViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: CategoryRepository




    //    private var _allTransactions: MutableLiveData<List<Transaction>> =  MutableLiveData<List<Transaction>>()
    var allCategories: LiveData<List<Category>>

    init {
        val CategoryDao = CategoryDatabase.getDatabase(application).CategoryDao()
        repository = CategoryRepository(CategoryDao)
        allCategories = repository.allCategories
    }

    fun insert(category: Category) = viewModelScope.launch {
        repository.insert(category)
    }

    fun getIncomeCategory() = repository.getIncomeCategory()
    fun getExpenseCategory() = repository.getExpenseCategory()

}
