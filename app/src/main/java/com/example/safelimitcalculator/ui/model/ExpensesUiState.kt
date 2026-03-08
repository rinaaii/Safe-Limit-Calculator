package com.example.safelimitcalculator.ui.model

import com.example.safelimitcalculator.data.entity.ExpenseEntity

data class ExpensesUiState(
    val isLoading: Boolean = true,
    val expenses: List<ExpenseEntity> = emptyList(),
    val error: String? = null
)