package com.depi.budgetapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentMainBinding

class MainFragment : Fragment() {


private lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.signBtn.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_sigupFragment)
        })

        binding.loginBtn.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_mainFragment_to_loginFragment)
        })
        return binding.root
    }
//    lateinit var aloginbtn: CardView
//    lateinit var asignbtn: CardView
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        aloginbtn=findViewById(R.id.login_btn)
//        asignbtn=findViewById(R.id.sign_btn)
//
//        aloginbtn.setOnClickListener(View.OnClickListener {
//            val intent= Intent(this,LoginFragment::class.java)
//            startActivity(intent)
//        })
//
//        asignbtn.setOnClickListener(View.OnClickListener {
//            val intent= Intent(this,SigupFragment::class.java)
//            startActivity(intent)
//        })
//    }
}