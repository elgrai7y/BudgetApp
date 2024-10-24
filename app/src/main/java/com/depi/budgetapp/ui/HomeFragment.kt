package com.depi.budgetapp.ui

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentHomeBinding
import com.google.android.material.navigation.NavigationView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.depi.budgetapp.adapters.TransactionAdapter
import com.depi.budgetapp.data.Transaction
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.util.UserPreferences
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory
import com.depi.budgetapp.viewmodels.TransactionViewModel
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment(),OnItemClickListener   {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var transvm: TransactionViewModel
    private lateinit var userPreferences: UserPreferences
    private var pos = 0.0
    private var neg = 0.0
    private var userBalance: Double = 0.0

    private lateinit var authRepository: AuthRepository

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        transvm = ViewModelProvider(this).get(TransactionViewModel::class.java)

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


        binding.showTv.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_allTransactionFragment2)
        })



        drawerLayout = binding.drawable

        val navigationView: NavigationView = binding.navView


        val headerView = navigationView.getHeaderView(0)
        val headerButton: Button = headerView.findViewById(R.id.profile_nv)
        headerButton.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.profileFragment)

            // Close the navigation drawer
        }
        val headerButton2: Button = headerView.findViewById(R.id.home_nv)
        headerButton2.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.homeFragment)

            // Close the navigation drawer
        }

        val headerButton3: Button = headerView.findViewById(R.id.trans_nv)
        headerButton3.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.allTransactionFragment2)

            // Close the navigation drawer
        }

        val headerButton4: Button = headerView.findViewById(R.id.category_nv)
        headerButton4.setOnClickListener {
            // Navigate to the target fragment when the button is clicked
            findNavController().navigate(R.id.manageCategoryFragment)

            // Close the navigation drawer
        }

        val headerButton5: Button = headerView.findViewById(R.id.logout)
        headerButton5.setOnClickListener{
            authViewModel.signOut()
            findNavController().navigate(R.id.mainFragment)
        }
        val lineChart = binding.lineChart  // No need to use findViewById


        val xAxis = lineChart.xAxis
// Step 2: Position the XAxis labels at the bottom of the chart
        xAxis.position = XAxis.XAxisPosition.BOTTOM

// Step 3: Optional: Disable gridlines if you want a cleaner look
        xAxis.setDrawGridLines(false)

// Step 4: Optional: Custom formatting (if you want labels like "Jan", "Feb", etc.)
// You can create a custom value formatter to provide custom labels for X-axis

// Step 5: Customize other X-axis properties like text color, size, etc.
        xAxis.textColor = Color.WHITE  // Customize label color
        xAxis.textSize = 12f

        val yAxis = lineChart.axisLeft
// Step 2: Position the XAxis labels at the bottom of the chart

// Step 3: Optional: Disable gridlines if you want a cleaner look
        yAxis.setDrawGridLines(false)

// Step 4: Optional: Custom formatting (if you want labels like "Jan", "Feb", etc.)
// You can create a custom value formatter to provide custom labels for X-axis

// Step 5: Customize other X-axis properties like text color, size, etc.
        yAxis.textColor = Color.WHITE  // Customize label color
        yAxis.textSize = 12f
// Create Data for Income
        val incomeEntries = ArrayList<Entry>()
        incomeEntries.add(Entry(1f, 50f))  // Example data
        incomeEntries.add(Entry(2f, 80f))
        incomeEntries.add(Entry(3f, 60f))
        incomeEntries.add(Entry(4f, 90f))
        incomeEntries.add(Entry(5f, 70f))
        incomeEntries.add(Entry(6f, 20f))


// Create Data for Expense
        val expenseEntries = ArrayList<Entry>()
        expenseEntries.add(Entry(1f, 70f))  // Example data
        expenseEntries.add(Entry(2f, 50f))
        expenseEntries.add(Entry(3f, 90f))
        expenseEntries.add(Entry(4f, 40f))
        expenseEntries.add(Entry(5f, 60f))
        expenseEntries.add(Entry(6f, 30f))

// Create DataSets for Income and Expense
        val incomeDataSet = LineDataSet(incomeEntries, "Income")
        incomeDataSet.color = Color.GREEN  // Set line color for income
        incomeDataSet.setCircleColor(Color.GREEN)  // Circle color for data points

        val expenseDataSet = LineDataSet(expenseEntries, "Expense")
        expenseDataSet.color = Color.RED  // Set line color for expense
        expenseDataSet.setCircleColor(Color.RED)  // Circle color for data points

// Customize the DataSets (optional, to make the chart look like in the image)
        incomeDataSet.fillAlpha = 110
        expenseDataSet.fillAlpha = 110
        incomeDataSet.setDrawFilled(true)
        expenseDataSet.setDrawFilled(true)
        incomeDataSet.fillColor = Color.GREEN
        expenseDataSet.fillColor = Color.RED

// Create a list of LineDataSets
        val dataSets = ArrayList<ILineDataSet>()
        dataSets.add(incomeDataSet)
        dataSets.add(expenseDataSet)

// Set data to the LineChart
        val data = LineData(dataSets)
        lineChart.data = data

// Customize other chart settings like background, gridlines, etc.
        lineChart.setBackgroundColor(Color.BLACK)  // Background color
        lineChart.setDrawGridBackground(false)  // No grid background
        lineChart.description.isEnabled = false  // Disable the description label
        lineChart.legend.textColor= Color.WHITE

        lineChart.legend.orientation = Legend.LegendOrientation.HORIZONTAL  // Set the legend orientation
        lineChart.legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM  // Position at the bottom
        lineChart.legend.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        lineChart.legend.form = Legend.LegendForm.CIRCLE  // Choose the line form for the legend
        lineChart.invalidate()


        val adapter=TransactionAdapter(this)
        val recyclerview=binding.transRv
        recyclerview.adapter=adapter
        recyclerview.layoutManager=LinearLayoutManager(requireContext())
        transvm = ViewModelProvider(this).get(TransactionViewModel::class.java)
        transvm.allTransactions.observe(viewLifecycleOwner, Observer {
                trans->adapter.setData(trans)
        })




        authRepository = AuthRepository()

        userPreferences = UserPreferences.getInstance(requireContext())

        transvm.getTotalIncomeAmount().observe(viewLifecycleOwner) {
            pos=it?: 0.0
            this.userBalance +=it.toString().toDouble()
            lifecycleScope.launch {
                userPreferences.balance.collect {
                        balance ->userBalance
                    binding.walletBalance.text = userBalance.toString()
                }
            }
        }
        transvm.getTotalExpenseAmount().observe(viewLifecycleOwner) {
            neg=it?: 0.0
            this.userBalance -=it.toString().toDouble()
            lifecycleScope.launch {
                userPreferences.balance.collect {
                        balance ->userBalance
                    binding.walletBalance.text = userBalance.toString()
                }
            }
        }

        lifecycleScope.launch {
            userPreferences.walletName.collect{walletName->
                binding.walletNameTv.text = walletName
            }
        }








        return binding.root
    }
    override fun onItemClick(transaction: Transaction) {
        val action = HomeFragmentDirections.actionHomeFragmentToEditTransactionFragment(transaction)
        findNavController().navigate(action)

    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }



}


