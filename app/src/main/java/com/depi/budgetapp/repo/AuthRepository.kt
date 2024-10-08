package com.depi.budgetapp.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class AuthRepository {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callback: (Result<FirebaseUser?>) -> Unit
    ) {

        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(firebaseAuth.currentUser))
                } else {
                    callback(Result.failure(task.exception ?: Exception("Authentication failed")))
                }
            }
    }

    fun signUpWithEmail(
        email: String,
        password: String,
        callback: (Result<FirebaseUser?>) -> Unit
    ) {
        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(Result.success(firebaseAuth.currentUser))
            } else {
                callback(Result.failure(task.exception ?: Exception("Authentication failed")))
            }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

}