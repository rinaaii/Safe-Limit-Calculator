package com.example.safelimitcalculator.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.safelimitcalculator.data.dao.ExpenseDao
import com.example.safelimitcalculator.data.dao.PaymentDao
import com.example.safelimitcalculator.data.entity.ExpenseEntity
import com.example.safelimitcalculator.data.entity.PaymentEntity
import com.example.safelimitcalculator.utils.DateConverters

@Database(
    entities = [
        ExpenseEntity::class,
        PaymentEntity::class
    ],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun expenseDao(): ExpenseDao
    abstract fun paymentDao(): PaymentDao

    companion object {
        const val DATABASE_NAME = "safe_limit_db"
    }
}