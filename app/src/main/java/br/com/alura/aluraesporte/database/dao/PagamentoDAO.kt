package br.com.alura.aluraesporte.database.dao

import androidx.room.Dao
import androidx.room.Insert
import br.com.alura.aluraesporte.model.Payment

@Dao
interface PagamentoDAO {

    @Insert
    fun salva(payment: Payment) : Long

}