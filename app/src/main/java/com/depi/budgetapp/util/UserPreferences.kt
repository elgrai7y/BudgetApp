package com.depi.budgetapp.util

import android.content.Context
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.Flow

class UserPreferences(private val context: Context) {

    private val Context.dataStore by preferencesDataStore("user_preferences")

    private object PreferencesKeys {
        val USER_NAME = stringPreferencesKey("user_name")
        val EMAIL = stringPreferencesKey("email")
        val WALLET_NAME = stringPreferencesKey("wallet_name")
        val BALANCE = doublePreferencesKey("balance")
    }

    val username: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.USER_NAME] ?: "Guest"

    }

    val email: Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.EMAIL] ?: "no email submitted"
    }

    val walletName:Flow<String> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.WALLET_NAME] ?: "your wallet"
    }

    val balance:Flow<Double> = context.dataStore.data.map { preferences ->
        preferences[PreferencesKeys.BALANCE] ?: 0.00
    }


    suspend fun saveUserInfo(username: String, email: String) {
        context.dataStore.edit {preferences->
            preferences[PreferencesKeys.USER_NAME] = username
            preferences[PreferencesKeys.EMAIL] = email
        }
    }

    suspend fun saveUserWalletName(walletName: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.WALLET_NAME] = walletName

        }

    }
    suspend fun saveUserBalance(balance: Double) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.BALANCE] = balance
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferences(context).also { INSTANCE = it }
            }
        }
    }
}