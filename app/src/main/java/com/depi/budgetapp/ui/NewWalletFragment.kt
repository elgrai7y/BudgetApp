package com.depi.budgetapp

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.databinding.FragmentNewWalletBinding
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.util.UserPreferences
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory
import kotlinx.coroutines.launch


class NewWalletFragment : Fragment() {

    private lateinit var binding: FragmentNewWalletBinding

    private lateinit var userPreferences: UserPreferences




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = FragmentNewWalletBinding.inflate(inflater, container, false)
        userPreferences = UserPreferences.getInstance(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener(
            View.OnClickListener {
                requireActivity().onBackPressed()
            }
        )

        binding.addWallet.setOnClickListener {
            if (TextUtils.isEmpty(binding.walletName.toString())) return@setOnClickListener

            val walletName = binding.walletName.text.toString()
            val balance = binding.walletBalance.text.toString()
            lifecycleScope.launch {

                userPreferences.saveUserWalletName(walletName)
            }
            lifecycleScope.launch {
                userPreferences.saveUserBalance(balance.toDouble())
            }

            findNavController().navigate(R.id.action_newWalletFragment3_to_homeFragment)

        }
    }


}