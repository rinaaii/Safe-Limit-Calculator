package com.example.safelimitcalculator.ui.model

import com.example.safelimitcalculator.data.model.Payment

data class PaymentDetailUiState(
    val isLoading: Boolean = true,
    val payment: Payment? = null
)