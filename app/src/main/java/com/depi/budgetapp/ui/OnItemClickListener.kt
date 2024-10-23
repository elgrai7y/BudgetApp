package com.depi.budgetapp.ui

import com.depi.budgetapp.data.Transaction

interface OnItemClickListener {

    fun onItemClick(transaction: Transaction)

}