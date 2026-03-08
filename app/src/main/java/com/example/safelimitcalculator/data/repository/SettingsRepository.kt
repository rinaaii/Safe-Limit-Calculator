package com.example.safelimitcalculator.data.repository

import com.example.safelimitcalculator.data.dao.SettingsDao
import com.example.safelimitcalculator.data.entity.SettingsEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class SettingsRepository(private val dao: SettingsDao) {

    val settingsFlow: Flow<SettingsEntity?> = dao.getSettings()

    suspend fun updateReserve(reserve: Float) {
        val current = dao.getSettingsOnce() ?: SettingsEntity(id = 1)
        dao.insert(current.copy(id = 1, reserve = reserve))
    }

    suspend fun getMinimumReserve(): Float {
        return dao.getReserve() ?: 0f
    }

    suspend fun updateNotifications(enabled: Boolean) {
        val current = dao.getSettingsOnce() ?: SettingsEntity(id = 1)
        dao.insert(current.copy(id = 1, notificationsEnabled = enabled))
    }

    suspend fun saveSettings(settings: SettingsEntity) {
        dao.insert(settings)
    }
}