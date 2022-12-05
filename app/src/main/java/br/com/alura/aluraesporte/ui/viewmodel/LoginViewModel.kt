package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.User
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository
import br.com.alura.aluraesporte.repository.LoginRepository
import br.com.alura.aluraesporte.repository.Resource

class LoginViewModel(
    private val repository: LoginRepository,
    private val firebaseAuthRepository: FirebaseAuthRepository
) : ViewModel() {

    fun authenticate(user: User): LiveData<Resource<Boolean>> =
        firebaseAuthRepository.authenticate(user)

    fun logOut() = firebaseAuthRepository.logOut()

    fun isLogged(): Boolean = firebaseAuthRepository.isLogged()

}
