package br.com.alura.aluraesporte.repository

import android.content.SharedPreferences
import androidx.core.content.edit

private const val KEY_LOGGED = "Logged"

class LoginRepository(private val preferences: SharedPreferences) {

    fun logIn() = save(state = true)

    fun logOut() = save(state = false)

    fun isLogged(): Boolean = preferences.getBoolean(KEY_LOGGED, false)

    private fun save(state: Boolean) {
        preferences.edit {
            putBoolean(KEY_LOGGED, state)
        }
    }

}
