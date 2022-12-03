package br.com.alura.aluraesporte.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal

@Entity(
    foreignKeys = [ForeignKey(
        entity = Product::class,
        parentColumns = ["id"],
        childColumns = ["produtoId"]
    )],
    indices = [Index("produtoId")]
)
class Payment(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val cardNumber: Int,
    val validityDate: String,
    val cvc: Int,
    val price: BigDecimal,
    val produtoId: Long
)