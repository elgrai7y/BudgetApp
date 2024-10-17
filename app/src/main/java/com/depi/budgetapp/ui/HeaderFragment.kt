package com.depi.budgetapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentHeaderBinding
import com.depi.budgetapp.databinding.FragmentHomeBinding


class HeaderFragment : Fragment() {
    private lateinit var binding: FragmentHeaderBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHeaderBinding.inflate(inflater, container, false)


        return binding.root
    }
}