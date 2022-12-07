package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.User
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository

class MyAccountViewModel(repository: FirebaseAuthRepository) : ViewModel() {

    val user: LiveData<User> = repository.getUser()

}