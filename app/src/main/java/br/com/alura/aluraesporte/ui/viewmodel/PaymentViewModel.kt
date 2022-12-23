package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Payment
import br.com.alura.aluraesporte.repository.PaymentRepository
import br.com.alura.aluraesporte.repository.ProductRepository

class PaymentViewModel(
    private val paymentRepository: PaymentRepository,
    private val productRepository: ProductRepository) : ViewModel() {

    fun save(payment: Payment) = paymentRepository.save(payment)
    fun searchProductById(id: String) = productRepository.searchById(id)
    fun all(): LiveData<List<Payment>> = paymentRepository.all()

}