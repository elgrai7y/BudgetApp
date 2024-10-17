package com.depi.budgetapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.databinding.FragmentMainBinding
import com.depi.budgetapp.databinding.FragmentSigupBinding

class SigupFragment : Fragment() {

    private lateinit var binding: FragmentSigupBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigupBinding.inflate(inflater, container, false)

        // Handle the back button click event
        binding.backBtn.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        })

        // Handle the login textView click event to move to Login Screen
        binding.loginTv.setOnClickListener(View.OnClickListener {
           findNavController().navigate(R.id.action_sigupFragment_to_loginFragment)
        })
        binding.signBtn.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_sigupFragment_to_newWalletFragment3)
        })



        return binding.root
    }

}