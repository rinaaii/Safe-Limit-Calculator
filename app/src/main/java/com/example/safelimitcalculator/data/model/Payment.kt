package com.example.safelimitcalculator.data.model

import org.threeten.bp.LocalDate

data class Payment(
    val id: Long = 0L,
    val name: String,
    val amount: Double,
    val date: LocalDate,
    val description: String
)