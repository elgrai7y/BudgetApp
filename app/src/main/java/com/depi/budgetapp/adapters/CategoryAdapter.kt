package com.depi.budgetapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.depi.budgetapp.data.Transaction
import android.graphics.Color
import android.view.View
import androidx.core.content.ContentProviderCompat.requireContext
import com.depi.budgetapp.R
import com.depi.budgetapp.data.Category
import com.depi.budgetapp.databinding.CategoryItemBinding
import com.depi.budgetapp.ui.OnCategoryClickListener
import com.depi.budgetapp.ui.OnItemClickListener
import com.google.android.material.bottomsheet.BottomSheetDialog


class CategoryAdapter(private val listener: OnCategoryClickListener):
    RecyclerView.Adapter<CategoryViewHolder>() {
    private var cateList= emptyList<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val itemBinding=
            CategoryItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CategoryViewHolder(itemBinding)
    }

    override fun getItemCount()= cateList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.transType.text= cateList[position].type.toString()
        holder.categoryname.text= cateList[position].categoryname.toString()
        if( holder.transType.text=="EXPENSE"){
            holder.transType.setTextColor(Color.parseColor("#FF928A"))
            holder.transic.setImageResource(R.drawable.ic_expense)
        }
        holder.itemView.setOnClickListener {
            listener.onCategoryClick(cateList[position])
        }
    }
    fun setData(cate:List<Category>)
    {
        this.cateList =cate
        notifyDataSetChanged()
    }
}


class CategoryViewHolder(itemView: CategoryItemBinding):RecyclerView.ViewHolder(itemView.root)
{
    val transType=itemView.transactionType
    val categoryname=itemView.categoryName
    val transic=itemView.icType


}
