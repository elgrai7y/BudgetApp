package com.depi.budgetapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.databinding.FragmentNewWalletBinding
import com.depi.budgetapp.databinding.FragmentSigupBinding


class NewWalletFragment : Fragment() {

    private lateinit var binding: FragmentNewWalletBinding




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_wallet, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backBtn.setOnClickListener(
            View.OnClickListener {


            }
        )
    }

}