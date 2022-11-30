package br.com.alura.aluraesporte.ui.viewmodel

import androidx.lifecycle.ViewModel
import br.com.alura.aluraesporte.model.Payment
import br.com.alura.aluraesporte.repository.PagamentoRepository
import br.com.alura.aluraesporte.repository.ProdutoRepository

class PagamentoViewModel(
    private val pagamentoRepository: PagamentoRepository,
    private val produtodRepository: ProdutoRepository) : ViewModel() {

    fun salva(payment: Payment) = pagamentoRepository.salva(payment)
    fun buscaProdutoPorId(id: Long) = produtodRepository.buscaPorId(id)

}