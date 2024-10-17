package com.depi.budgetapp.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.depi.budgetapp.R
import com.depi.budgetapp.databinding.FragmentLoginBinding
import com.depi.budgetapp.repo.AuthRepository
import com.depi.budgetapp.viewmodels.AuthViewModel
import com.depi.budgetapp.viewmodels.AuthViewModelFactory
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseUser

class LoginFragment : Fragment() {
    private lateinit var authRepository: AuthRepository

    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var googleSignInLauncher: ActivityResultLauncher<Intent>

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
        // Step 1: Setup Google Sign-In
        setupGoogleSignIn()


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

        binding.googleImageV.setOnClickListener(View.OnClickListener {
            signInWithGoogle()
        })

        observeAuthViewModel()

        // Initialize the launcher for Google Sign-In
        googleSignInLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                handleSignInResult(task)
            }
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
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)

    }


    // Step 4: Set up Google Sign-In
    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client_id)) // Request the ID token
            .requestEmail() // Request email for the sign-in
            .build()

        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    // Step 5: Trigger Google Sign-In flow
    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        googleSignInLauncher.launch(signInIntent)
    }

    // Step 6: Handle Google Sign-In result and retrieve the idToken
    private fun handleSignInResult(task: Task<GoogleSignInAccount>) {
        try {
            val account = task.getResult(Exception::class.java)
            val idToken = account?.idToken
            if (idToken != null) {
                // Pass the idToken to the ViewModel for Firebase authentication
                authViewModel.signInWithGoogle(idToken)
            }
        } catch (e: Exception) {
            Toast.makeText(
                requireActivity(),
                "Google Sign-In failed: ${e.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Step 7: Handle successful sign-in
    private fun handleSignInSuccess(user: FirebaseUser?) {
        user?.let {
            Toast.makeText(requireActivity(), "Welcome ${user.displayName}", Toast.LENGTH_SHORT)
                .show()
        }
    }


}