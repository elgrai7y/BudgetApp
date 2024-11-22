package com.depi.budgetapp.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.depi.budgetapp.R
import com.depi.budgetapp.data.Category
import com.depi.budgetapp.data.TransactionType
import com.depi.budgetapp.databinding.FragmentAddCategoryBinding
import com.depi.budgetapp.viewmodels.CategoryViewModel

class AddCategoryFragment : Fragment() {

    private lateinit var binding: FragmentAddCategoryBinding
    private lateinit var transvm: CategoryViewModel
    private var isincome: Boolean? = null
    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddCategoryBinding.inflate(inflater, container, false)


        binding.backBtn.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        })


        val clickListener = View.OnClickListener { view ->

            when (view.getId()) {
                R.id.edit_income_button -> {
                    isincome = true
                    onClick(view,"income")
                }
                R.id.edit_expense_button -> {
                    isincome = false
                    onClick(view,"expense")

                }
            }
        }


        binding.editIncomeButton.setOnClickListener(clickListener)
        binding.editExpenseButton.setOnClickListener(clickListener)


        transvm = ViewModelProvider(this).get(CategoryViewModel::class.java)
        binding.addButton.setOnClickListener(View.OnClickListener {
            insertData()

        })


        return binding.root
    }

    private fun insertData() {
        val transCategory = binding.categoryName.text.toString()




        if (!transCategory.toString().isEmpty() &&  isincome == true) {
            val category = Category(
                0,
                TransactionType.INCOME,
                transCategory,

                )
            Toast.makeText(requireActivity(), "ok", Toast.LENGTH_SHORT).show()

            transvm.insert(category)
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
        else if(!transCategory.toString().isEmpty()  && isincome == false) {
            val category = Category(
                0,
                TransactionType.EXPENSE,
                transCategory,

                )
            Toast.makeText(requireActivity(), "ok3", Toast.LENGTH_SHORT).show()

            transvm.insert(category)
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }
        else Toast.makeText(requireActivity(), "complete info please", Toast.LENGTH_SHORT).show()

    }

    @Override
    fun onClick(v: View?,s:String) {
        // Change button background color on click
        if(s=="income") {
            binding.editIncomeButton.setBackgroundColor(android.graphics.Color.parseColor("#FDCB08"))
            binding.editIncomeButton.setTextColor((android.graphics.Color.parseColor("#FF000000")))
            binding.editExpenseButton.setBackgroundColor(android.graphics.Color.parseColor("#4DAB3A3A"))
            binding.editExpenseButton.setTextColor((android.graphics.Color.parseColor("#FDA09A")))
        }
        else{
            binding.editExpenseButton.setBackgroundColor(android.graphics.Color.parseColor("#FDCB08"))
            binding.editExpenseButton.setTextColor((android.graphics.Color.parseColor("#FF000000")))
            binding.editIncomeButton.setBackgroundColor(android.graphics.Color.parseColor("#5729662C"))
            binding.editIncomeButton.setTextColor((android.graphics.Color.parseColor("#6FFF74")))
        }
    }

}