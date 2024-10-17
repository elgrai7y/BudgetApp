package com.depi.budgetapp.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentLoadingBinding


class LoadingFragment : Fragment() {

    private lateinit var binding: FragmentLoadingBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.action_loadingFragment_to_mainFragment)
        },2000)

        binding = FragmentLoadingBinding.inflate(inflater, container, false)



        return binding.root
    }

}

