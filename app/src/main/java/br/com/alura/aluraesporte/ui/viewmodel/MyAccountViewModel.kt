package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.FirebaseAuthRepository

class MyAccountViewModel(private val repository: FirebaseAuthRepository) : ViewModel() {
    val email: String = "test@alurasport.com"
}