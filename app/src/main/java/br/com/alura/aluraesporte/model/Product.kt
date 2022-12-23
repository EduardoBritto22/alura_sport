package br.com.alura.aluraesporte.model

import java.math.BigDecimal

class Product(
    val id: String? = null,
    val name: String = "",
    val price: BigDecimal = BigDecimal.ZERO
)
