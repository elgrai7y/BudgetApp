package com.depi.budgetapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentHomeBinding
import com.depi.budgetapp.databinding.FragmentMainBinding

import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout




class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var toggle: ActionBarDrawerToggle



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)


        binding.toolbar.setNavigationOnClickListener {
            if ( binding.drawable.isDrawerOpen(GravityCompat.START)) {
                binding.drawable.closeDrawer(GravityCompat.START)
            } else {
                binding.drawable.openDrawer(GravityCompat.START)
            }
        }


        toggle= ActionBarDrawerToggle(activity, binding.drawable , R.string.nav_open, R.string.nav_close)
        binding.drawable.addDrawerListener(toggle)
        toggle.syncState()

        return binding.root
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (toggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
    }

}