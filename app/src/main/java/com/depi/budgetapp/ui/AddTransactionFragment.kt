package com.depi.budgetapp.ui

import android.icu.text.SimpleDateFormat
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.depi.budgetapp.R
import com.depi.budgetapp.adapters.CategoryAdapter
import com.depi.budgetapp.data.Category
import com.depi.budgetapp.data.Transaction
import com.depi.budgetapp.data.TransactionType
import com.depi.budgetapp.databinding.FragmentAddTransactionBinding
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory
import com.depi.budgetapp.viewmodels.CategoryViewModel
import com.depi.budgetapp.viewmodels.TransactionViewModel
import com.depi.budgetapp.viewmodels.TransactionViewModelFactory
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import java.util.Date
import java.util.Locale


@AddTransactionFragment.AndroidEntryPoint
class AddTransactionFragment : Fragment(), OnCategoryClickListener {
    annotation class AndroidEntryPoint

    private lateinit var binding: FragmentAddTransactionBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomSheetDialog: BottomSheetDialog


    private lateinit var selectedDateText: TextView
    private lateinit var balanceEditText: EditText
    private lateinit var transvm: TransactionViewModel
    private var isincome: Boolean? = null
    private lateinit var transvm2: CategoryViewModel

    private lateinit var authRepository: AuthRepository

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddTransactionBinding.inflate(inflater, container, false)

        val clickListener = View.OnClickListener { view ->

            when (view.getId()) {
                R.id.edit_income_button -> isincome = true
                R.id.edit_expense_button -> isincome = false
            }
        }


        binding.editIncomeButton.setOnClickListener(clickListener)
        binding.editExpenseButton.setOnClickListener(clickListener)

        //************************************
        val factory = TransactionViewModelFactory(requireActivity().application)

        transvm = ViewModelProvider(this, factory).get(TransactionViewModel::class.java)
        binding.addBtn.setOnClickListener(View.OnClickListener {
            insertData()
        })

        //************************************

        binding.bottomNavigation.setupWithNavController(findNavController())


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


        return binding.root
    }
    //************************************

    private fun insertData() {
        val transBalance = binding.editBalanceEditText.text.toString().toDoubleOrNull()
        val transCategory = binding.categoryName.text.toString()
        val selectedDateString = binding.editSelectedDateText.text.toString()

        val transDate: Date =
            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(selectedDateString)



        if (transBalance != null && transDate != null && isincome == true) {
            val transaction = Transaction(
                0,
                TransactionType.INCOME,
                transCategory,
                transBalance,
                transDate
            )

            Toast.makeText(requireActivity(), "$transBalance", Toast.LENGTH_SHORT).show()

            transvm.insert(transaction)
        } else if (transBalance != null && transDate != null && isincome == false) {
            val transaction = Transaction(
                0,
                TransactionType.EXPENSE,
                transCategory,
                transBalance,
                transDate
            )
            Toast.makeText(requireActivity(), "$transBalance", Toast.LENGTH_SHORT).show()

            transvm.insert(transaction)
        } else Toast.makeText(requireActivity(), "complete info please", Toast.LENGTH_SHORT).show()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize views
        selectedDateText = view.findViewById(R.id.edit_selected_date_text)
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
        binding.editBalanceEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val input = s.toString()
                if (input.isNotEmpty()) {
                    val value = input.toDoubleOrNull()
                    if (value == null) {
                        binding.editBalanceEditText.error = "Please enter a valid number"
                    }
                }
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

}