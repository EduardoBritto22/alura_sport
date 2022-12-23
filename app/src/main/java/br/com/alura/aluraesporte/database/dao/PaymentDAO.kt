package br.com.alura.aluraesporte.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import br.com.alura.aluraesporte.model.Payment

@Dao
interface PaymentDAO {

    @Insert
    fun salva(payment: Payment) : Long

    @Query("SELECT * FROM Payment")
    fun all(): LiveData<List<Payment>>

}