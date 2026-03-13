package com.example.safelimitcalculator.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore by preferencesDataStore(name = "settings")

object SettingsKeys {
    val RESERVE = floatPreferencesKey("reserve")
    val CURRENT_BALANCE = floatPreferencesKey("current_balance")
    val NEXT_INCOME_DATE = stringPreferencesKey("next_income_date")
    val NOTIFICATIONS_ENABLED = booleanPreferencesKey("notifications_enabled")
}