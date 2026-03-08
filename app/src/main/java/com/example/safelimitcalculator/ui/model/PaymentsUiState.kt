package com.example.safelimitcalculator.ui.model

import com.example.safelimitcalculator.data.entity.PaymentEntity

data class PaymentsUiState(
    val isLoading: Boolean = true,
    val payments: List<PaymentEntity> = emptyList()
)