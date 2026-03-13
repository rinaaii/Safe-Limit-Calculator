package com.example.safelimitcalculator.ui.model

import com.example.safelimitcalculator.data.model.Payment

data class PaymentsUiState(
    val isLoading: Boolean = true,
    val payments: List<Payment> = emptyList()
)