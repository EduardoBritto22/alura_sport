package br.com.alura.aluraesporte.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity
class Product(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "",
    val price: BigDecimal = BigDecimal.ZERO
)
