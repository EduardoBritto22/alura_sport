package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.LoginRepository

class LoginViewModel(private val repository: LoginRepository): ViewModel() {

    fun logIn(){
        repository.logIn()
    }

    fun logOut() {
        repository.logOut()
    }

    fun isLogged(): Boolean = repository.isLogged()

}
