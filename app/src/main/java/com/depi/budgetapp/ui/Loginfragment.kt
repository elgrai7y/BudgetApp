package com.depi.budgetapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentLoginBinding
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.util.isValidEmail
import com.depi.budgetapp.util.isValidPassword
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {
    private lateinit var authRepository: AuthRepository

//    private lateinit var authViewModel: AuthViewModel
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(authRepository)
        }

    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)



        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        authRepository = AuthRepository()
        // Initialize the ViewModel



        // Set click listener for the back imageView to make onBackPress
        binding.backBtn.setOnClickListener(View.OnClickListener {
            requireActivity().onBackPressedDispatcher.onBackPressed()
        })
        // Set click listener for the "sign up" textView to go to signUp screen
        binding.signTv.setOnClickListener(View.OnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_sigupFragment)
        })

        //use login button to login in firebase
        binding.loginBtn.setOnClickListener(View.OnClickListener {
            val email = binding.emailEt.text.toString()
            val password = binding.passEt.text.toString()

            login(email,password)
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
        Toast.makeText(requireActivity() , exception.message, Toast.LENGTH_LONG).show()
    }

    // Call these methods to login or register
    private fun login(email: String, password: String) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(requireActivity(), "Please insert your email and password", Toast.LENGTH_SHORT).show()
            return
        }
        authViewModel.login(email, password)
    }



}