package com.example.safelimitcalculator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "expenses")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val amount: Double,
    val category: String,
    val date: LocalDate
)