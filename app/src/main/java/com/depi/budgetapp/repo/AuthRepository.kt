package com.depi.budgetapp.repo

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    


    fun signInWithEmailAndPassword(
        email: String,
        password: String,
        callback: (Result<FirebaseUser?>) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback(Result.success(auth.currentUser))
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
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                callback(Result.success(auth.currentUser))
            } else {
                callback(Result.failure(task.exception ?: Exception("Authentication failed")))
            }
        }
    }

    suspend fun signInWithGoogle(idToken: String): Result<FirebaseUser?> {
        return try {
            val credential = GoogleAuthProvider.getCredential(idToken, null)
            val authResult = auth.signInWithCredential(credential).await()
            Result.success(authResult.user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    fun signOut() {
        auth.signOut()
    }

    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }


}