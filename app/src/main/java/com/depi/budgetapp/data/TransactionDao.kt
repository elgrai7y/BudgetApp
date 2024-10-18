package com.depi.budgetapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface TransactionDao {

    //insert Transaction
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY date DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Update
    suspend fun update(transaction: Transaction)

    @Delete
    suspend fun delete(transaction: Transaction)

    //get all transactions



    //get all income transactions
    @Query("SELECT * FROM transaction_table WHERE type = 'INCOME'")
    fun getIncomeTransactions(): Flow<List<Transaction>>

    //get all expense transactions
    @Query("SELECT * FROM transaction_table WHERE type = 'EXPENSE'")
    fun getExpenseTransactions(): Flow<List<Transaction>>

    //get all transactions by time range
    @Query("SELECT * FROM transaction_table WHERE date >= :startDate AND date <= :endDate")
    fun getAllTransactionsForDateRange(startDate: Date, endDate: Date): Flow<List<Transaction>>

    //get all transactions for today
    @Query("SELECT * FROM transaction_table WHERE date = :today")
    fun getAllTransactionsForToday(today: Date): Flow<List<Transaction>>

    // Fetch income transactions for specific periods (today, week, month, year)
    @Query("SELECT * FROM transaction_table WHERE type = 'INCOME' AND date BETWEEN :startDate AND :endDate")
    fun getIncomeTransactionsForDateRange(startDate: Date, endDate: Date): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE type = 'EXPENSE' AND date BETWEEN :startDate AND :endDate")
    fun getExpenseTransactionsForDateRange(startDate: Date, endDate: Date): Flow<List<Transaction>>

    // Today's income transactions
    @Query("SELECT * FROM transaction_table WHERE type = 'INCOME' AND date = :today")
    fun getTodayIncomeTransactions(today: Date): Flow<List<Transaction>>

    // Today's expense transactions
    @Query("SELECT * FROM transaction_table WHERE type = 'EXPENSE' AND date = :today")
    fun getTodayExpenseTransactions(today: Date): Flow<List<Transaction>>


}
