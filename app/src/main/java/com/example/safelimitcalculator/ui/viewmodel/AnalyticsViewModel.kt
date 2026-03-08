package com.example.safelimitcalculator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.data.entity.ExpenseEntity
import com.example.safelimitcalculator.data.repository.ExpenseRepository
import com.example.safelimitcalculator.ui.model.AnalyticsUiState
import com.example.safelimitcalculator.utils.AnalyticsPeriod
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class AnalyticsViewModel(
    private val expenseRepository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AnalyticsUiState())
    val uiState: StateFlow<AnalyticsUiState> = _uiState.asStateFlow()

    init {
        observeExpenses()
    }

    fun onPeriodChanged(period: AnalyticsPeriod) {
        _uiState.update { it.copy(selectedPeriod = period) }
        observeExpenses()
    }

    private fun observeExpenses() {
        viewModelScope.launch {
            expenseRepository.allExpenses.collect { expenses ->
                calculateAnalytics(expenses)
            }
        }
    }

    private fun calculateAnalytics(expenses: List<ExpenseEntity>) {

        val period = _uiState.value.selectedPeriod
        val today = LocalDate.now()

        val startDate = when (period) {
            AnalyticsPeriod.DAY -> today
            AnalyticsPeriod.WEEK -> today.minusDays(6)
            AnalyticsPeriod.MONTH -> today.minusDays(29)
        }

        val filtered = expenses.filter {
            !it.date.isBefore(startDate) && !it.date.isAfter(today)
        }

        val total = filtered.sumOf { it.amount.toDouble() }.toFloat()

        val daysCount = when (period) {
            AnalyticsPeriod.DAY -> 1
            AnalyticsPeriod.WEEK -> 7
            AnalyticsPeriod.MONTH -> 30
        }

        val average = if (daysCount > 0) total / daysCount else 0f

        val groupedByDate = filtered
            .groupBy { it.date }
            .mapValues { entry ->
                entry.value.sumOf { it.amount.toDouble() }.toFloat()
            }
            .toSortedMap()

        val groupedByCategory = filtered
            .groupBy { it.category }
            .mapValues { entry ->
                entry.value.sumOf { it.amount.toDouble() }.toFloat()
            }

        _uiState.update {
            it.copy(
                totalAmount = total,
                averagePerDay = average,
                dailyData = groupedByDate.toList(),
                categoryData = groupedByCategory.toList(),
                isLoading = false
            )
        }
    }
}