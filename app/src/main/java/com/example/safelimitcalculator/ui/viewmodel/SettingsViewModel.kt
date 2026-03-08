package com.example.safelimitcalculator.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.data.entity.SettingsEntity
import com.example.safelimitcalculator.data.repository.SettingsRepository
import com.example.safelimitcalculator.notifications.FinanceNotificationManager
import com.example.safelimitcalculator.ui.model.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.threeten.bp.LocalDate
import kotlinx.coroutines.launch
import org.threeten.bp.temporal.ChronoUnit

class SettingsViewModel(
    private val repository: SettingsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.settingsFlow.collect { settings ->
                settings?.let {
                    _uiState.update {
                        it.copy(
                            reserve = settings.reserve.toString(),
                            currentBalance = settings.currentBalance.toString(),
                            nextIncomeDate = settings.nextIncomeDate,
                            notificationsEnabled = settings.notificationsEnabled,
                            isLoading = false
                        )
                    }
                } ?: _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun onBalanceChanged(newBalance: String) {
        _uiState.update { it.copy(currentBalance = newBalance) }
    }

    fun onDateChanged(date: LocalDate) {
        _uiState.update { it.copy(nextIncomeDate = date) }
    }

    fun onReserveChanged(newReserve: String) {
        _uiState.update { it.copy(reserve = newReserve) }

        val amount = newReserve.toFloatOrNull()
        if (amount != null) {
            viewModelScope.launch {
                repository.updateReserve(amount)
            }
        }
    }

    fun onNotificationsChanged(enabled: Boolean) {
        _uiState.update { it.copy(notificationsEnabled = enabled) }
    }

    fun saveSettings(context: Context) {
        viewModelScope.launch {
            val reserveValue = _uiState.value.reserve.toFloatOrNull() ?: 0f
            val balanceValue = _uiState.value.currentBalance.toDoubleOrNull() ?: 0.0
            val nextIncomeDate = _uiState.value.nextIncomeDate

            val settings = SettingsEntity(
                id = 1,
                reserve = reserveValue,
                notificationsEnabled = _uiState.value.notificationsEnabled,
                currentBalance = balanceValue,
                nextIncomeDate = nextIncomeDate
            )

            repository.saveSettings(settings)

            nextIncomeDate?.let { incomeDate ->
                val today = LocalDate.now()
                val daysUntilIncome = ChronoUnit.DAYS.between(today, incomeDate)

                if (daysUntilIncome < 2 && settings.notificationsEnabled) {
                    FinanceNotificationManager.checkIncomeReminder(
                        context = context,
                        incomeAmount = balanceValue,
                        incomeDate = incomeDate
                    )
                }
            }
        }
    }
}