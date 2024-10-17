package com.depi.budgetapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentEditTransactionBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class EditTransactionFragment : Fragment() {

    private lateinit var selectedDateText: TextView
    private lateinit var dollarIcon: ImageView
    private lateinit var balanceEditText: EditText
    private lateinit var binding: FragmentEditTransactionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTransactionBinding.inflate(inflater, container, false)
        binding.bottomNavigation.setupWithNavController( findNavController())

        return binding.root }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        selectedDateText = view.findViewById(R.id.edit_selected_date_text)
        dollarIcon = view.findViewById(R.id.edit_dollar_icon)
        balanceEditText = view.findViewById(R.id.edit_balance_edit_text)

        // Setup EditText TextWatcher
        setupBalanceEditText()

        // Setup Date Picker Bottom Sheet
        view.findViewById<LinearLayout>(R.id.edit_date_picker_layout).setOnClickListener {
            showDatePickerBottomSheet()
        }

        // Setup Category Button Bottom Sheet
        view.findViewById<LinearLayout>(R.id.edit_category_button).setOnClickListener {
            showCategoryBottomSheet()
        }
    }

    // Function to handle text changes in the balance EditText
    private fun setupBalanceEditText() {
        balanceEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                dollarIcon.visibility = if (s.isNullOrEmpty()) View.VISIBLE else View.GONE
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    // Function to show the DatePicker bottom sheet
    private fun showDatePickerBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val sheetView: View = LayoutInflater.from(requireContext())
            .inflate(R.layout.bottom_sheet_date_picker, null)

        val datePicker = sheetView.findViewById<DatePicker>(R.id.datePicker)
        sheetView.findViewById<View>(R.id.confirm_button).setOnClickListener {
            val day = datePicker.dayOfMonth
            val month = datePicker.month + 1 // DatePicker months are zero-indexed
            val year = datePicker.year
            selectedDateText.text = getString(R.string.selected_date_format, day, month, year)
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        bottomSheetDialog.show()
    }

    // Function to show the Category selection bottom sheet
    private fun showCategoryBottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val sheetView: View = LayoutInflater.from(requireContext())
            .inflate(R.layout.category_bottom_sheet, null)

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        bottomSheetDialog.show()
    }
}
