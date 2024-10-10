package com.depi.budgetapp.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transaction_table ORDER BY id ASC")
    fun getAlltransactions(): LiveData<List<Transaction>>
}
