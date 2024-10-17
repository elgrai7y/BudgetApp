package com.depi.budgetapp.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
<<<<<<< HEAD:app/src/main/java/com/depi/budgetapp/BaseActivity.kt
import androidx.navigation.ui.setupWithNavController
import com.depi.budgetapp.databinding.ActivityBaseBinding
import com.depi.budgetapp.databinding.FragmentSigupBinding


class BaseActivity : AppCompatActivity() {
    lateinit var binding: ActivityBaseBinding
=======
import com.depi.budgetapp.R
import com.google.android.material.navigation.NavigationView


class BaseActivity : AppCompatActivity() {

>>>>>>> sub_branch:app/src/main/java/com/depi/budgetapp/ui/BaseActivity.kt
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityBaseBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(R.layout.activity_base)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets


        }

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.appbar.setupWithNavController(navController)


    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_container)
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}



