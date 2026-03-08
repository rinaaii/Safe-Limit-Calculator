package com.example.safelimitcalculator.ui.model

import org.threeten.bp.LocalDate

data class AddEditPaymentUiState(
    val name: String = "",
    val amount: String = "",
    val description: String = "",
    val date: LocalDate = LocalDate.now(),
    val isLoading: Boolean = false,
    val error: String? = null
)