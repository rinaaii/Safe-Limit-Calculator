package com.example.safelimitcalculator.ui.model

import com.example.safelimitcalculator.data.model.Expense

data class ExpensesUiState(
    val isLoading: Boolean = true,
    val expenses: List<Expense> = emptyList(),
    val error: String? = null
)