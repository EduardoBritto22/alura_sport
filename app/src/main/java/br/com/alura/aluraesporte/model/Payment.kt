package br.com.alura.aluraesporte.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity()
class Payment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardNumber: Int,
    val validityDate: String,
    val cvc: Int,
    val price: BigDecimal,
    val productId: String
)