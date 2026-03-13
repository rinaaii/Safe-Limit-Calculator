package com.example.safelimitcalculator.data.model

import org.threeten.bp.LocalDate

data class Settings(
    val reserve: Float = 0f,
    val currentBalance: Float = 0f,
    val nextIncomeDate: LocalDate? = null,
    val notificationsEnabled: Boolean = true
)