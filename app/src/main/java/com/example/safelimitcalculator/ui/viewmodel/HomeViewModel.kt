package com.example.safelimitcalculator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.data.entity.SettingsEntity
import com.example.safelimitcalculator.data.repository.ExpenseRepository
import com.example.safelimitcalculator.data.repository.PaymentRepository
import com.example.safelimitcalculator.data.repository.SettingsRepository
import com.example.safelimitcalculator.ui.model.HomeUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

class HomeViewModel(
    private val expenseRepository: ExpenseRepository,
    private val paymentRepository: PaymentRepository,
    private val settingsRepository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        observeHomeData()
    }

    private fun calculateSafeDailyLimit(
        balance: Float,
        reserve: Float,
        upcomingPaymentsSum: Float,
        totalSpent: Float,
        daysLeft: Long
    ): Float {

        val availableMoney = balance - totalSpent - reserve - upcomingPaymentsSum

        return if (daysLeft > 0 && availableMoney > 0) {
            availableMoney / daysLeft
        } else {
            0f
        }
    }

    private fun observeHomeData() {
        viewModelScope.launch {
            combine(
                expenseRepository.allExpenses,
                paymentRepository.allPayments,
                settingsRepository.settingsFlow
            ) { expenses, payments, settings ->

                val today = LocalDate.now()
                val settingsSafe = settings ?: SettingsEntity(id = 1)

                val balance = settingsSafe.currentBalance.toFloat()
                val reserve = settingsSafe.reserve
                val nextIncomeDate = settingsSafe.nextIncomeDate

                // 1. Считаем ВСЕ потраченные деньги (чтобы лимит уменьшался при покупках)
                val totalSpent = expenses.sumOf { it.amount.toDouble() }.toFloat()

                // 2. Считаем дни до зарплаты
                val daysLeft = if (nextIncomeDate != null) {
                    val days = ChronoUnit.DAYS.between(today, nextIncomeDate)
                    if (days <= 0) 1 else days
                } else 1

                // 3. Считаем будущие платежи (только те, что сегодня или позже)
                val upcomingPayments = payments.filter { it.date >= today }
                val totalUpcomingSum = upcomingPayments.sumOf { it.amount.toDouble() }.toFloat()

                // 4. Считаем траты именно за СЕГОДНЯ (для заголовка на главном)
                val allTodayExpenses = expenses.filter { it.date == today }
                val totalSpentToday = allTodayExpenses.sumOf { it.amount.toDouble() }.toFloat()

                // 5. Вызываем расчет с учетом totalSpent
                val safeLimit = calculateSafeDailyLimit(
                    balance = balance,
                    reserve = reserve,
                    upcomingPaymentsSum = totalUpcomingSum,
                    totalSpent = totalSpent, // <-- Теперь лимит будет падать при новых тратах
                    daysLeft = daysLeft
                )

                HomeUiState(
                    isLoading = false,
                    totalExpensesSum = totalSpentToday,
                    todayExpenses = allTodayExpenses.sortedByDescending { it.id }.take(5),
                    upcomingPayments = upcomingPayments.sortedBy { it.date },
                    safeLimit = safeLimit,
                    minimumReserve = reserve,
                    currentBalance = balance,
                    daysLeft = daysLeft
                )
            }.collect {
                _uiState.value = it
            }
        }
    }
}

