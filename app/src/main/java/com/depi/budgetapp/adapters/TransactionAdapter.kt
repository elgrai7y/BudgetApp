package com.depi.budgetapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.depi.budgetapp.data.Transaction
import com.depi.budgetapp.databinding.TransactionItemBinding
import com.depi.budgetapp.ui.OnItemClickListener
import androidx.core.content.ContextCompat
import android.graphics.Color
import com.depi.budgetapp.R

class TransactionAdapter(private val listener: OnItemClickListener) :
    RecyclerView.Adapter<TransactionViewHolder>() {
    private var transList = emptyList<Transaction>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        val itemBinding =
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(itemBinding)
    }

    override fun getItemCount() = transList.size

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        holder.transType.text= transList[position].type.toString()
        holder.transDate.text= transList[position].date.toString()
        holder.transBalance.text= transList[position].amount.toString()
        if( holder.transType.text=="EXPENSE"){
            holder.transType.setTextColor(Color.parseColor("#FF928A"))
            holder.transic.setImageResource(R.drawable.ic_expense)
        }
        holder.itemView.setOnClickListener {
            listener.onItemClick(transList[position])
        }
    }


    fun setData(user: List<Transaction>) {
        this.transList = user

        notifyDataSetChanged()
    }
}



class TransactionViewHolder(itemView: TransactionItemBinding):RecyclerView.ViewHolder(itemView.root)
{
val transType=itemView.transactionType
    val transDate=itemView.transactionDate
    val transCategory=itemView.transactionCategory
    val transBalance=itemView.transactionAmount
    val transic=itemView.icType



}
