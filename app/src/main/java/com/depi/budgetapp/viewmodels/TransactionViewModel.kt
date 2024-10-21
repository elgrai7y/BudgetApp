package com.depi.budgetapp.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.depi.budgetapp.data.Category
import com.depi.budgetapp.data.Transaction
import com.depi.budgetapp.data.TransactionDatabase
import com.depi.budgetapp.repo.TransactionRepository
import kotlinx.coroutines.launch
import com.depi.budgetapp.repo.CategoryRepository

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private var repository: TransactionRepository




    //    private var _allTransactions: MutableLiveData<List<Transaction>> =  MutableLiveData<List<Transaction>>()
    var allTransactions: LiveData<List<Transaction>>

    init {
        val TransactionDao = TransactionDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(TransactionDao)
        allTransactions = repository.allTransactions
    }

    fun insert(transaction: Transaction) = viewModelScope.launch {
        repository.insert(transaction)
    }

    fun update(transaction: Transaction) = viewModelScope.launch {
        repository.update(transaction)
    }

    fun delete(transaction: Transaction) = viewModelScope.launch {
        repository.delete(transaction)
    }

    fun getIncomeTransactions() = repository.getIncomeTransactions()
    fun getExpenseTransactions() = repository.getExpenseTransactions()
    fun getTransactionsForToday() = repository.getTransactionsForToday()
    fun getWeeklyTransactions() = repository.getWeeklyTransactions()
    fun getMonthlyTransactions() = repository.getMonthlyTransactions()
    fun getYearlyTransactions() = repository.getYearlyTransactions()
    fun getTodayIncomeTransactions() = repository.getTodayIncomeTransactions()
    fun getWeeklyIncomeTransactions() = repository.getWeeklyIncomeTransactions()
    fun getMonthlyIncomeTransactions() = repository.getMonthlyIncomeTransactions()
    fun getYearlyIncomeTransactions() = repository.getYearlyIncomeTransactions()
    fun getTodayExpenseTransactions() = repository.getTodayExpenseTransactions()
    fun getWeeklyExpenseTransactions() = repository.getWeeklyExpenseTransactions()
    fun getMonthlyExpenseTransactions() = repository.getMonthlyExpenseTransactions()
    fun getYearlyExpenseTransactions() = repository.getYearlyExpenseTransactions()

}
