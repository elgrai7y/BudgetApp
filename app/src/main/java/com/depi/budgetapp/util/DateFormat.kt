package com.depi.budgetapp.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date

fun formatDate(date: Date): String {
    val formatter = SimpleDateFormat("dd/MM/yy")
    return formatter.format(date)
}

fun parseDate(dateString: String): Date {
    val formatter = SimpleDateFormat("dd/MM/yy")

    return formatter.parse(dateString)


}
