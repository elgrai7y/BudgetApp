package com.depi.budgetapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.depi.budgetapp.repo.AuthRepository
import com.google.firebase.auth.FirebaseUser


class AuthViewModel(private val authRepository: AuthRepository):ViewModel() {

    private val _authState = MutableLiveData<Result<FirebaseUser?>>()
    val authState: LiveData<Result<FirebaseUser?>> get() = _authState


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

    // to make sign out from Qash
    fun signOut() {
        authRepository.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return authRepository.getCurrentUser()
    }

}