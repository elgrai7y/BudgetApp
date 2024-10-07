package com.depi.budgetapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    lateinit var asign:TextView
    lateinit var aback:ImageView

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        // Set click listener for the back imageView to make onBackPress
        binding.backBtn.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        })
        // Set click listener for the "sign up" textView to go to signUp screen
        binding.signTv.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_sigupFragment)
        })

        return binding.root
    }


}