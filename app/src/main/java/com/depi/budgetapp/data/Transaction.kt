package com.depi.budgetapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val type: Boolean,
    val category: String,
    val date: Date,
    val amount:Int,


)