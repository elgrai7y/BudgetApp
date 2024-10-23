package com.depi.budgetapp.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

import java.util.Date

@Parcelize
@Entity(tableName = "transaction_table")
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: TransactionType,
    val category: String,
    val amount: Double,
    val date:Date
): Parcelable {

    init {
        if (amount <= 0) require(amount > 0)
    }

}

