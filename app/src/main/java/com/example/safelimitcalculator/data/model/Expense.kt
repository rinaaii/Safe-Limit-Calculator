package com.example.safelimitcalculator.data.model

import org.threeten.bp.LocalDate

data class Expense(
    val id: Long,
    val amount: Double,
    val category: String,
    val date: LocalDate
)