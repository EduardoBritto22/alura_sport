package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository
import br.com.alura.aluraesporte.repository.Resource

class RegisterUserViewModel(private val repository: FirebaseAuthRepository): ViewModel() {

    fun registerUser(email: String, password: String): LiveData<Resource<Boolean>> {
        return repository.registerUser(email, password)
    }

}