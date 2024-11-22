package com.depi.budgetapp.ui

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depi.budgetapp.R
import com.depi.budgetapp.adapters.CategoryAdapter
import com.depi.budgetapp.data.Category
import com.depi.budgetapp.data.Transaction
import com.depi.budgetapp.data.TransactionType
import com.depi.budgetapp.databinding.FragmentEditTransactionBinding
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.util.formatDate
import com.depi.budgetapp.util.parseDate
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory
import com.depi.budgetapp.viewmodels.CategoryViewModel
import com.depi.budgetapp.viewmodels.TransactionViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import java.util.Date
import java.util.Locale


class EditTransactionFragment : Fragment(), OnCategoryClickListener {

    private lateinit var selectedDateText: TextView
    private lateinit var dollarIcon: ImageView
    private lateinit var balanceEditText: EditText
    private lateinit var binding: FragmentEditTransactionBinding
    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var transvm: TransactionViewModel
    private val args by navArgs<EditTransactionFragmentArgs>()
    private var isincome: Boolean? = null
    private lateinit var transvm2: CategoryViewModel
    private lateinit var authRepository: AuthRepository
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTransactionBinding.inflate(inflater, container, false)
        binding.toolbar.setNavigationOnClickListener {
            if (binding.drawable.isDrawerOpen(GravityCompat.START)) {
                binding.drawable.closeDrawer(GravityCompat.START)
            } else {
                binding.drawable.openDrawer(GravityCompat.START)
            }
        }
        toggle =
            ActionBarDrawerToggle(activity, binding.drawable, R.string.nav_open, R.string.nav_close)
        binding.drawable.addDrawerListener(toggle)
        toggle.syncState()
        drawerLayout = binding.drawable
        val navigationView: NavigationView = binding.navView
        val headerView = navigationView.getHeaderView(0)
        val headerButton: Button = headerView.findViewById(R.id.profile_nv)
        headerButton.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.profileFragment)

            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        val headerButton2: Button = headerView.findViewById(R.id.home_nv)
        headerButton2.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.homeFragment)

            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val headerButton3: Button = headerView.findViewById(R.id.trans_nv)
        headerButton3.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.allTransactionFragment2)

            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val headerButton4: Button = headerView.findViewById(R.id.category_nv)
        headerButton4.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.manageCategoryFragment)

            // Close the navigation drawer
            drawerLayout.closeDrawer(GravityCompat.START)
        }

        val headerButton5: Button = headerView.findViewById(R.id.logout)
        headerButton5.setOnClickListener {
            authViewModel.signOut()
            findNavController().navigate(R.id.mainFragment)
        }

        transvm = ViewModelProvider(this).get(TransactionViewModel::class.java)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transvm = ViewModelProvider(this).get(TransactionViewModel::class.java)

        isincome =args.currentTransaction.isincome

            if (isincome ==true) {
                onClick(view, "income")
            } else {
                onClick(view, "expense")
            }



        val clickListener = View.OnClickListener { view ->

            when (view.getId()) {
                R.id.edit_income_button -> {
                    isincome = true

                    onClick(view, "income")
                }

                R.id.edit_expense_button -> {
                    isincome = false

                    onClick(view, "expense")

                }
            }
        }
        binding.editIncomeButton.setOnClickListener(clickListener)
        binding.editExpenseButton.setOnClickListener(clickListener)

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


        binding.categoryName.text =
            Editable.Factory.getInstance().newEditable(args.currentTransaction.category)
        binding.editBalanceEditText.text =
            Editable.Factory.getInstance().newEditable(args.currentTransaction.amount.toString())

        /***********************/


        binding.editSelectedDateText.text = formatDate(args.currentTransaction.date)

        //
        binding.saveChangeBtn.setOnClickListener(View.OnClickListener {
            editeAlart()
        })
        binding.deleteBtn.setOnClickListener(View.OnClickListener {
            deleteAlart()
        })
        binding.backBtn.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()

        })
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
        val sheetView = LayoutInflater.from(requireContext())
            .inflate(R.layout.category_bottom_sheet, null)

        // Create the BottomSheetDialog
        bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(sheetView)
        val recyclerView: RecyclerView = sheetView.findViewById(R.id.trans_rv)

        val adapter = CategoryAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        transvm2 = ViewModelProvider(this).get(CategoryViewModel::class.java)
        transvm2.allCategories.observe(viewLifecycleOwner, Observer { cate ->
            adapter.setData(cate)
        })

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        bottomSheetDialog.show()
    }

    override fun onCategoryClick(category: Category) {
        binding.categoryName.text = category.categoryname
        bottomSheetDialog.dismiss()
    }

    private fun editeAlart() {

        val builder = AlertDialog.Builder(requireContext())

        // Set the message show for the Alert time
        builder.setMessage("Do you want to save changes ?")

        // Set Alert Title

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false)

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes") {


                dialog, which ->
            dialog.dismiss()
                edite()



            requireActivity().onBackPressedDispatcher.onBackPressed()

        }

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No") {
            // If user click no then dialog box is canceled.
                dialog, which ->
            dialog.cancel()
        }

        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }

    private fun deleteAlart() {

        val builder = AlertDialog.Builder(requireContext())

        // Set the message show for the Alert time
        builder.setMessage("Are you sure you want to Delete this Transaction ?")

        // Set Alert Title

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false)

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("Yes") {


                dialog, which ->
            dialog.dismiss()
            val amount: Double = binding.editBalanceEditText.text.toString().toDouble()
            val date: Date = parseDate(binding.editSelectedDateText.text.toString())
            val category = binding.categoryName.text.toString()
            val currentTransaction = Transaction(
                amount = amount,
                date = date,
                category = category,
                type = args.currentTransaction.type,
                id = args.currentTransaction.id,
                isincome = args.currentTransaction.isincome
            )
            transvm.delete(currentTransaction)
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No") {
            // If user click no then dialog box is canceled.
                dialog, which ->
            dialog.cancel()
        }

        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }


    private fun edite() {
        val transBalance = binding.editBalanceEditText.text.toString().toDoubleOrNull()
        val transCategory = binding.categoryName.text.toString()
        val selectedDateString = binding.editSelectedDateText.text.toString()

        val transDate: Date =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(selectedDateString)



        if (transBalance != null && transDate != null&& isincome==true ) {
            val transaction = Transaction(
                id = args.currentTransaction.id,
                TransactionType.INCOME,
                transCategory,
                transBalance,
                transDate,
                true
            )

            Toast.makeText(requireActivity(), "$transBalance", Toast.LENGTH_SHORT).show()

            transvm.update(transaction)

        }
        else if (transBalance != null && transDate != null&&isincome==false) {
            val transaction = Transaction(
                id = args.currentTransaction.id,
                TransactionType.EXPENSE,
                transCategory,
                transBalance,
                transDate,
                false
            )
            Toast.makeText(requireActivity(), "$transBalance", Toast.LENGTH_SHORT).show()

            transvm.update(transaction)

        } else {
            Toast.makeText(requireActivity(), "complete info please", Toast.LENGTH_SHORT).show()

        }
    }


    @Override
    fun onClick(v: View?, s: String) {
        // Change button background color on click
        if (s == "income") {
            binding.editIncomeButton.setBackgroundColor(android.graphics.Color.parseColor("#FDCB08"))
            binding.editIncomeButton.setTextColor((android.graphics.Color.parseColor("#FF000000")))
            binding.editExpenseButton.setBackgroundColor(android.graphics.Color.parseColor("#4DAB3A3A"))
            binding.editExpenseButton.setTextColor((android.graphics.Color.parseColor("#FDA09A")))
        } else {
            binding.editExpenseButton.setBackgroundColor(android.graphics.Color.parseColor("#FDCB08"))
            binding.editExpenseButton.setTextColor((android.graphics.Color.parseColor("#FF000000")))
            binding.editIncomeButton.setBackgroundColor(android.graphics.Color.parseColor("#5729662C"))
            binding.editIncomeButton.setTextColor((android.graphics.Color.parseColor("#6FFF74")))
        }
    }


}
