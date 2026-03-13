package com.example.safelimitcalculator.data.repository

import com.example.safelimitcalculator.data.model.Settings
import android.content.Context
import android.system.Os.remove
import androidx.datastore.preferences.core.edit
import com.example.safelimitcalculator.data.SettingsKeys
import com.example.safelimitcalculator.data.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate

class SettingsRepository(private val context: Context) {

    val settingsFlow: Flow<Settings> = context.dataStore.data
        .map { prefs ->
            val dateStr = prefs[SettingsKeys.NEXT_INCOME_DATE]
            val parsedDate = dateStr?.takeIf { it.isNotBlank() }?.let {
                try {
                    LocalDate.parse(it)
                } catch (e: Exception) {
                    null
                }
            }
            Settings(
                reserve = prefs[SettingsKeys.RESERVE] ?: 0f,
                currentBalance = prefs[SettingsKeys.CURRENT_BALANCE] ?: 0f,
                nextIncomeDate = parsedDate,
                notificationsEnabled = prefs[SettingsKeys.NOTIFICATIONS_ENABLED] ?: true
            )
        }

    suspend fun updateReserve(value: Float) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.RESERVE] = value
        }
    }

    suspend fun updateCurrentBalance(value: Float) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.CURRENT_BALANCE] = value
        }
    }

    suspend fun updateNextIncomeDate(date: LocalDate) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.NEXT_INCOME_DATE] = date.toString()
        }
    }

    suspend fun updateNotifications(enabled: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.NOTIFICATIONS_ENABLED] = enabled
        }
    }

    suspend fun resetSettings() {
        context.dataStore.edit { prefs ->
            prefs[SettingsKeys.RESERVE] = 0f
            prefs[SettingsKeys.CURRENT_BALANCE] = 0f
            prefs.remove(SettingsKeys.NEXT_INCOME_DATE)
            prefs[SettingsKeys.NOTIFICATIONS_ENABLED] = true
        }
    }

    suspend fun clearAllData() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }

    suspend fun getNotificationsEnabled(): Boolean {
        val prefs = context.dataStore.data.first()
        return prefs[SettingsKeys.NOTIFICATIONS_ENABLED] ?: true
    }
}