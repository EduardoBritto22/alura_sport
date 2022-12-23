package br.com.alura.aluraesporte.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.alura.aluraesporte.database.converter.ConversorBigDecimal
import br.com.alura.aluraesporte.database.dao.PaymentDAO
import br.com.alura.aluraesporte.model.Payment

@Database(
    version = 3,
    entities = [Payment::class],
    exportSchema = false
)
@TypeConverters(ConversorBigDecimal::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun paymentDAO(): PaymentDAO
}