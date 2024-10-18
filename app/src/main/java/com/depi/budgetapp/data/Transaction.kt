package com.depi.budgetapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import java.util.Date

@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: TransactionType,
    val category: String,
    val amount: Double,
    val date:Date
) {

    init {
        if (amount <= 0) require(amount > 0)
    }

}

