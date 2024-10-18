package com.depi.budgetapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentSigupBinding
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.util.arePasswordsEqual
import com.depi.budgetapp.util.isValidEmail
import com.depi.budgetapp.util.isValidPassword
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory
import com.google.firebase.auth.FirebaseUser

class SigupFragment : Fragment() {

    private lateinit var binding: FragmentSigupBinding

    private lateinit var authRepository: AuthRepository


    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSigupBinding.inflate(inflater, container, false)


        // init view
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authRepository = AuthRepository()


        // Handle the back button click event
        binding.backBtn.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        })

        // Handle the login textView click event to move to Login Screen
        binding.loginTv.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_sigupFragment_to_loginFragment)
        })

        binding.signBtn.setOnClickListener(View.OnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passEt.text.toString()
            val confirmPassword = binding.confirmPassEt.text.toString()
            register(email, password, confirmPassword)

        })

        binding.googleImageV.setOnClickListener(View.OnClickListener {

        })

        observeAuthViewModel()

    }


    private fun observeAuthViewModel() {
        authViewModel.authState.observe(requireActivity()) { result ->
            result.fold(
                onSuccess = { user -> handleSuccess(user) },
                onFailure = { error -> handleError(error) }
            )
        }
    }

    private fun handleSuccess(user: FirebaseUser?) {
        // Navigate to main screen or show a success message
        Toast.makeText(requireActivity(), "Welcome ${user?.email}", Toast.LENGTH_SHORT).show()

    }

    private fun handleError(exception: Throwable) {
        // Show error message
        Toast.makeText(requireActivity(), exception.message, Toast.LENGTH_LONG).show()
    }

    private fun register(email: String, password: String, confirmPassword: String) {
        if (!isValidEmail(email)) {
            Toast.makeText(requireActivity(), "not valid Email form", Toast.LENGTH_SHORT).show()
            return
        }
        if (!isValidPassword(password)) {
            Toast.makeText(
                requireActivity(),
                "not valid password(6 chars include 1 letter and 1 digit)",
                Toast.LENGTH_SHORT
            ).show()
            return
        }
        if (!arePasswordsEqual(password, confirmPassword)) {
            Toast.makeText(requireActivity(), "Passwords do not match", Toast.LENGTH_SHORT).show()
            return
        }


        authViewModel.register(email, password)
        findNavController().navigate(R.id.action_sigupFragment_to_newWalletFragment3)

    }


}