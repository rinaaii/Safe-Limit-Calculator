package com.example.safelimitcalculator.ui.model

import org.threeten.bp.LocalDate

data class AddEditExpenseUiState(
    val amount: String = "",
    val category: String = "General",
    val date: LocalDate = LocalDate.now(),
    val isEditMode: Boolean = false,
    val error: String? = null
)