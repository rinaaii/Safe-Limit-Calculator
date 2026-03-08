package com.example.safelimitcalculator.ui.model

import com.example.safelimitcalculator.data.entity.ExpenseEntity
import com.example.safelimitcalculator.data.entity.PaymentEntity
import org.threeten.bp.LocalDate

data class HomeUiState(
    val isLoading: Boolean = false,
    val totalExpensesSum: Float = 0f,
    val todayExpenses: List<ExpenseEntity> = emptyList(),
    val upcomingPayments: List<PaymentEntity> = emptyList(),
    val safeLimit: Float = 0f,
    val minimumReserve: Float = 0f,
    val currentBalance: Float = 0f,
    val nextIncomeDate: LocalDate? = null,
    val daysLeft: Long = 0
)