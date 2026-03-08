package com.example.safelimitcalculator.ui.model

import com.example.safelimitcalculator.utils.AnalyticsPeriod
import org.threeten.bp.LocalDate

data class AnalyticsUiState(
    val selectedPeriod: AnalyticsPeriod = AnalyticsPeriod.WEEK,
    val totalAmount: Float = 0f,
    val averagePerDay: Float = 0f,
    val dailyData: List<Pair<LocalDate, Float>> = emptyList(),
    val categoryData: List<Pair<String, Float>> = emptyList(),
    val isLoading: Boolean = true
)