package br.com.alura.aluraesporte.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.util.*

fun BigDecimal.formatToBrazilianCurrency(): String {
    val brazilianFormat = DecimalFormat
        .getCurrencyInstance(Locale("pt", "br"))
    return brazilianFormat
        .format(this)
}