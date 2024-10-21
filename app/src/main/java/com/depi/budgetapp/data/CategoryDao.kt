package com.depi.budgetapp.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)



    @Query("SELECT * FROM category_table ORDER BY id DESC")
    fun getAllCAtegory(): Flow<List<Category>>



    //get all income transactions
    @Query("SELECT * FROM category_table WHERE type = 'INCOME'")
    fun getIncomeCategory(): Flow<List<Category>>

    //get all expense transactions
    @Query("SELECT * FROM category_table WHERE type = 'EXPENSE'")
    fun getExpenseCategory(): Flow<List<Category>>
}