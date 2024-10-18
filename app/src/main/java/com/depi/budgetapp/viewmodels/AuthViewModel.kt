package com.depi.budgetapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.depi.budgetapp.repo.AuthRepository
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _authState = MutableLiveData<Result<FirebaseUser?>>()
    val authState: LiveData<Result<FirebaseUser?>> get() = _authState

    private val _isAuthenticated = MutableStateFlow<Boolean?>(null) // Holds auth status
    val isAuthenticated: StateFlow<Boolean?> get() = _isAuthenticated


    fun login(email: String, password: String) {
        authRepository.signInWithEmailAndPassword(email, password) { result ->
            _authState.postValue(result)
        }
    }

    fun register(email: String, password: String) {
        authRepository.signUpWithEmail(email, password) { result ->
            _authState.postValue(result)
        }
    }

    // Sign in with Google by passing the idToken to the repository
    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            val result = authRepository.signInWithGoogle(idToken)
            _authState.value = result
        }
    }

    fun checkAuthState() {
        viewModelScope.launch {
            delay(2000)
            _isAuthenticated.value = getCurrentUser() != null
        }
    }


    // to make sign out from Qash
    fun signOut() {
        authRepository.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }


}