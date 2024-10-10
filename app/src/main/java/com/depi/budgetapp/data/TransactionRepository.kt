package com.depi.budgetapp.data

import androidx.lifecycle.LiveData

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions: LiveData<List<Transaction>> = transactionDao.getAlltransactions()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
}
