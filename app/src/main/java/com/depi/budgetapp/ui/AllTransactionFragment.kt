package com.depi.budgetapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentAddTransactionBinding
import com.depi.budgetapp.databinding.FragmentAllTransaction2Binding


class AllTransactionFragment : Fragment() {
    private lateinit var binding: FragmentAllTransaction2Binding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAllTransaction2Binding.inflate(inflater, container, false)
        binding.bottomNavigation.setupWithNavController( findNavController())

        return binding.root }


}