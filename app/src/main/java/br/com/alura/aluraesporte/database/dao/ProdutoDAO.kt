package br.com.alura.aluraesporte.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.aluraesporte.model.Product

@Dao
interface ProdutoDAO {

    @Query("SELECT * FROM Product")
    fun buscaTodos(): LiveData<List<Product>>

    @Insert
    fun salva(vararg product: Product)

    @Query("SELECT * FROM Product WHERE id = :id")
    fun buscaPorId(id: Long): LiveData<Product>

}
