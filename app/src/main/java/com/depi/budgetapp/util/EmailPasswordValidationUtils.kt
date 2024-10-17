package com.depi.budgetapp.util

// ValidationUtils.kt
fun isValidEmail(email: String): Boolean {
    val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    return email.matches(Regex(emailPattern))
}

fun isValidPassword(password: String): Boolean {
    // Minimum six characters, at least one letter and one number
    val passwordPattern = "^(?=.*[0-9])(?=.*[a-zA-Z]).{6,}$"
    return password.matches(Regex(passwordPattern))
}

fun arePasswordsEqual(password: String, confirmPassword: String): Boolean {
    return password == confirmPassword
}

