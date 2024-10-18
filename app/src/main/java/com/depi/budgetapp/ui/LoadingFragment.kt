package com.depi.budgetapp.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentLoadingBinding
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory


class LoadingFragment : Fragment() {

    private lateinit var binding: FragmentLoadingBinding
    private lateinit var authRepository: AuthRepository
    private val authViewMode: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

//        Handler(Looper.getMainLooper()).postDelayed({
//            findNavController().navigate(R.id.action_loadingFragment_to_mainFragment)
//        }, 2000)

        binding = FragmentLoadingBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        authRepository = AuthRepository()
        authViewMode.checkAuthState()
        lifecycleScope.launchWhenStarted {
            authViewMode.isAuthenticated.collect { isAuthenticated ->
                when (isAuthenticated) {
                    true -> findNavController().navigate(R.id.action_loadingFragment_to_homeFragment)
                    false -> findNavController().navigate(R.id.action_loadingFragment_to_mainFragment)
                    else ->{

                    }
                }
            }
        }
    }

}

