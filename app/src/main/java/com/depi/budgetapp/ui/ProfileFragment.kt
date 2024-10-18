package com.depi.budgetapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentHomeBinding
import com.depi.budgetapp.databinding.FragmentProfileBinding
import com.google.android.material.navigation.NavigationView


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var drawerLayout: DrawerLayout

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
binding.bottomNavigation.setupWithNavController( findNavController())
        return binding.root
    }



}