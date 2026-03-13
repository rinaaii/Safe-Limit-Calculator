package com.example.safelimitcalculator.ui.viewmodel

import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.data.repository.SettingsRepository
import com.example.safelimitcalculator.notifications.NotificationPermission.areNotificationsEnabled
import com.example.safelimitcalculator.ui.model.SettingsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import android.provider.Settings
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class SettingsViewModel(
    private val repository: SettingsRepository,
    private val context: Context
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState())
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val systemEnabled = areNotificationsEnabled(context)
            repository.settingsFlow.collect { settings ->
                _uiState.update {
                    it.copy(
                        reserve = settings.reserve.toString(),
                        currentBalance = settings.currentBalance.toString(),
                        nextIncomeDate = settings.nextIncomeDate,
                        notificationsEnabled = settings.notificationsEnabled && systemEnabled,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun onReserveChanged(value: String) {
        _uiState.update { it.copy(reserve = value) }
        value.toFloatOrNull()?.let { amount ->
            viewModelScope.launch { repository.updateReserve(amount) }
        }
    }

    fun onBalanceChanged(value: String) {
        _uiState.update { it.copy(currentBalance = value) }
        value.toFloatOrNull()?.let { amount ->
            viewModelScope.launch { repository.updateCurrentBalance(amount) }
        }
    }

    fun onDateChanged(date: LocalDate) {
        _uiState.update { it.copy(nextIncomeDate = date) }
        viewModelScope.launch { repository.updateNextIncomeDate(date) }
    }

    fun onNotificationsChanged(enabled: Boolean, onBlocked: () -> Unit) {
        viewModelScope.launch {
            val systemEnabled = areNotificationsEnabled()

            if (enabled && !systemEnabled) {
                _uiState.update { it.copy(notificationsEnabled = false) }
                onBlocked()
                return@launch
            }

            _uiState.update { it.copy(notificationsEnabled = enabled) }
            repository.updateNotifications(enabled)
        }
    }

    private fun areNotificationsEnabled(): Boolean {
        return NotificationManagerCompat.from(context).areNotificationsEnabled()
    }

    fun openNotificationSettings() {
        val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS).apply {
            putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}