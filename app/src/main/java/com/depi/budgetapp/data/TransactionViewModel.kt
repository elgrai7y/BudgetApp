package com.depi.budgetapp.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class TransactionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: TransactionRepository
    val allTransaction: LiveData<List<Transaction>>

    init {
        val TransactionDao =TransactionDatabase.getDatabase(application).transactionDao()
        repository = TransactionRepository(TransactionDao)
        allTransaction = repository.allTransactions
    }

    fun insert(transactions: Transaction) = viewModelScope.launch {
        repository.insert(transactions)
    }
}
