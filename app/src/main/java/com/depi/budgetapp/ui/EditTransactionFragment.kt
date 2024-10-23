package com.depi.budgetapp.ui

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.data.Transaction
import com.depi.budgetapp.data.TransactionType
import com.depi.budgetapp.util.formatDate
import com.depi.budgetapp.util.parseDate
import com.depi.budgetapp.viewmodels.TransactionViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.navigation.NavigationView
import java.text.SimpleDateFormat
import java.util.Date
import com.depi.budgetapp.databinding.FragmentEditTransactionBinding
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory


class EditTransactionFragment : Fragment() {

    private lateinit var selectedDateText: TextView
    private lateinit var dollarIcon: ImageView
    private lateinit var balanceEditText: EditText
    private lateinit var binding: FragmentEditTransactionBinding

    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var transvm: TransactionViewModel
    private val args by navArgs<EditTransactionFragmentArgs>()

    private lateinit var authRepository: AuthRepository
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditTransactionBinding.inflate(inflater, container, false)
        binding.bottomNavigation.setupWithNavController( findNavController())


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
        headerButton5.setOnClickListener{
            authViewModel.signOut()
            findNavController().navigate(R.id.mainFragment)
        }

        transvm = ViewModelProvider(this).get(TransactionViewModel::class.java)


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

        binding.editBalanceEditText.text = Editable.Factory.getInstance().newEditable(args.currentTransaction.amount.toString())

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
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val sheetView: View = LayoutInflater.from(requireContext())
            .inflate(R.layout.category_bottom_sheet, null)

        bottomSheetDialog.setContentView(sheetView)
        bottomSheetDialog.setCanceledOnTouchOutside(true)
        bottomSheetDialog.show()
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


                dialog, which -> dialog.dismiss()
            val amount:Double = binding.editBalanceEditText.text.toString().toDouble()
            val date:Date = parseDate(binding.editSelectedDateText.text.toString())
            val currentTransaction = Transaction(amount = amount, date = date, category = args.currentTransaction.category, type = TransactionType.INCOME, id = args.currentTransaction.id)

            transvm.update(currentTransaction)
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No") {
            // If user click no then dialog box is canceled.
                dialog, which -> dialog.cancel()
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


                dialog, which -> dialog.dismiss()
            val amount:Double = binding.editBalanceEditText.text.toString().toDouble()
            val date:Date = parseDate(binding.editSelectedDateText.text.toString())
            val currentTransaction = Transaction(amount = amount, date = date, category = args.currentTransaction.category, type = TransactionType.INCOME, id = args.currentTransaction.id)

            transvm.delete(currentTransaction)
            requireActivity().onBackPressedDispatcher.onBackPressed()

        }

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("No") {
            // If user click no then dialog box is canceled.
                dialog, which -> dialog.cancel()
        }

        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }




}
