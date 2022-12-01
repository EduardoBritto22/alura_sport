package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Product
import br.com.alura.aluraesporte.repository.ProductRepository

class ProductsViewModel(private val repository: ProductRepository) : ViewModel() {

    fun searchAll(): LiveData<List<Product>> = repository.buscaTodos()

}
