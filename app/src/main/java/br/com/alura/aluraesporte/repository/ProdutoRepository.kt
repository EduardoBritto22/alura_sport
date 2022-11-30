package br.com.alura.aluraesporte.repository

import androidx.lifecycle.LiveData
import br.com.alura.aluraesporte.database.dao.ProdutoDAO
import br.com.alura.aluraesporte.model.Product

class ProdutoRepository(private val dao: ProdutoDAO) {

    fun buscaTodos(): LiveData<List<Product>> = dao.buscaTodos()

    fun buscaPorId(id: Long): LiveData<Product> = dao.buscaPorId(id)

}
