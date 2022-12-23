package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.repository.ProductRepository

class ProductDetailsViewModel(
    productId: String,
    repository: ProductRepository
) : ViewModel() {

    val productFound = repository.searchById(productId)

}