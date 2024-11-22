package com.depi.budgetapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.map
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentProfileBinding
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.util.UserPreferences
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory
import com.depi.budgetapp.viewmodels.TransactionViewModel
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var userPreferences: UserPreferences

    private var userBalance: Double = 0.0
    private var numofIncome = 0
    private var numOfExpense = 0
    private var pos = 0.0
    private var neg = 0.0
    private lateinit var transvm: TransactionViewModel
    private lateinit var authRepository: AuthRepository

    //    private lateinit var authViewModel: AuthViewModel
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }


    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

            binding = FragmentProfileBinding.inflate(inflater, container, false)



        binding.toolbar.setNavigationOnClickListener {
            if (binding.drawable.isDrawerOpen(GravityCompat.START)) {
                binding.drawable.closeDrawer(GravityCompat.START)
            } else {
                binding.drawable.openDrawer(GravityCompat.START)
            }
        }

        authRepository = AuthRepository()
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

    transvm = ViewModelProvider(this).get(TransactionViewModel::class.java)

    transvm.getIncomeTransactions().observe(viewLifecycleOwner) { it ->
        numofIncome = it.size
        binding.numi.text = numofIncome.toString()
    }

    transvm.getExpenseTransactions().observe(viewLifecycleOwner) {
        numOfExpense = it.size
        binding.nume.text = numOfExpense.toString()

    }

    userPreferences = UserPreferences.getInstance(requireContext())
        val headerButton5: Button = headerView.findViewById(R.id.logout)
        headerButton5.setOnClickListener{
            authViewModel.signOut()
            findNavController().navigate(R.id.mainFragment)
        }

        transvm.getTotalIncomeAmount().observe(viewLifecycleOwner) {
            pos = it ?: 0.0
            this.userBalance += pos.toString().toDouble()

            lifecycleScope.launch {
                userPreferences.balance.collect { balance ->
                    userBalance
                    binding.walletBalance.text = userBalance.toString()
                }
            }
        }
        transvm.getTotalExpenseAmount().observe(viewLifecycleOwner) {
            neg = it ?: 0.0
                this.userBalance -= neg.toString().toDouble()
            // this.userBalance -= it.toString().toDouble()
            lifecycleScope.launch {
                userPreferences.balance.collect { balance ->
                    userBalance
                    binding.walletBalance.text = userBalance.toString()
                }
            }
        }




        lifecycleScope.launch {

            userPreferences.walletName.collect{walletName->
                binding.currWallet.text = walletName
            }
        }

        return binding.root

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            userPreferences.username.collect { username ->
                binding.username.text = username

            }
        }

        lifecycleScope.launch {
            userPreferences.email.collect { email ->
                binding.emailTv.text = email
            }
        }


//        binding.walletBalance.text = netBalance().toString()
    }


}