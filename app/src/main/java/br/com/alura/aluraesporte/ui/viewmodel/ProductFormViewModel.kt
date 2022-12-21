package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Product
import br.com.alura.aluraesporte.repository.ProductRepository

class ProductFormViewModel(private val repository: ProductRepository) : ViewModel() {

    fun save(product: Product): LiveData<Boolean> = repository.save(product)

}
