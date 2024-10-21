package com.depi.budgetapp.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.depi.budgetapp.data.Transaction
import com.depi.budgetapp.data.TransactionDao
import com.depi.budgetapp.util.DateHelper

class TransactionRepository(private val transactionDao: TransactionDao) {

    val allTransactions: LiveData<List<Transaction>> =
        transactionDao.getAllTransactions().asLiveData()

    suspend fun insert(transaction: Transaction) {
        transactionDao.insert(transaction)
    }

    suspend fun update(transaction: Transaction) {
        transactionDao.update(transaction)
    }

    suspend fun delete(transaction: Transaction) {
        transactionDao.delete(transaction)
    }


//    fun getAllTransactions() = transactionDao.getAllTransactions()

    fun getIncomeTransactions() = transactionDao.getIncomeTransactions().asLiveData()

    fun getExpenseTransactions() = transactionDao.getExpenseTransactions().asLiveData()

    fun getTransactionsForToday() =
        transactionDao.getAllTransactionsForToday(DateHelper.getToday()).asLiveData()

    fun getWeeklyTransactions() =
        transactionDao.getAllTransactionsForDateRange(
            DateHelper.getStartOfWeek(),
            DateHelper.getEndOfToday()
        ).asLiveData()

    fun getMonthlyTransactions() =
        transactionDao.getAllTransactionsForDateRange(
            DateHelper.getStartOfMonth(),
            DateHelper.getEndOfToday()
        ).asLiveData()

    fun getYearlyTransactions() =
        transactionDao.getAllTransactionsForDateRange(
            DateHelper.getStartOfYear(),
            DateHelper.getEndOfToday()
        ).asLiveData()

    // New methods for income and expense based on time filters
    fun getTodayIncomeTransactions() =
        transactionDao.getTodayIncomeTransactions(DateHelper.getToday()).asLiveData()

    fun getWeeklyIncomeTransactions() =
        transactionDao.getIncomeTransactionsForDateRange(
            DateHelper.getStartOfWeek(),
            DateHelper.getEndOfToday()
        ).asLiveData()

    fun getMonthlyIncomeTransactions() =
        transactionDao.getIncomeTransactionsForDateRange(
            DateHelper.getStartOfMonth(),
            DateHelper.getEndOfToday()
        ).asLiveData()

    fun getYearlyIncomeTransactions() =
        transactionDao.getIncomeTransactionsForDateRange(
            DateHelper.getStartOfYear(),
            DateHelper.getEndOfToday()
        ).asLiveData()

    fun getTodayExpenseTransactions() =
        transactionDao.getTodayExpenseTransactions(DateHelper.getToday())

    fun getWeeklyExpenseTransactions() =
        transactionDao.getExpenseTransactionsForDateRange(
            DateHelper.getStartOfWeek(),
            DateHelper.getEndOfToday()
        ).asLiveData()

    fun getMonthlyExpenseTransactions() =
        transactionDao.getExpenseTransactionsForDateRange(
            DateHelper.getStartOfMonth(),
            DateHelper.getEndOfToday()
        ).asLiveData()

    fun getYearlyExpenseTransactions() =
        transactionDao.getExpenseTransactionsForDateRange(
            DateHelper.getStartOfYear(),
            DateHelper.getEndOfToday()
        ).asLiveData()

    fun getTotalIncomeAmount() = transactionDao.getTotalIncomeAmount().asLiveData()
    fun getTotalExpenseAmount() = transactionDao.getTotalExpenseAmount().asLiveData()

}
