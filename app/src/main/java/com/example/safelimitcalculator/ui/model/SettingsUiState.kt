package com.example.safelimitcalculator.ui.model

import org.threeten.bp.LocalDate

data class SettingsUiState(
    val isLoading: Boolean = true,
    val reserve: String = "",
    val currentBalance: String = "",
    val nextIncomeDate: LocalDate? = null,
    val notificationsEnabled: Boolean = true
)