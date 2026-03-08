package com.example.safelimitcalculator.ui.model

import com.example.safelimitcalculator.data.entity.PaymentEntity

data class PaymentDetailUiState(
    val isLoading: Boolean = true,
    val payment: PaymentEntity? = null
)