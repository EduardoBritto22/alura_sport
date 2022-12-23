package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.ProductRepository

class ProductDetailsViewModel(
    private val productId: String,
    private val repository: ProductRepository
) : ViewModel() {

    val productFound = repository.searchById(productId)

    fun delete() = repository.delete(productId)

}