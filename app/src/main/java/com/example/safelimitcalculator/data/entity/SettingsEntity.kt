package com.example.safelimitcalculator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val id: Int = 1,
    val reserve: Float = 0.0f,
    val notificationsEnabled: Boolean = true,
    val currentBalance: Double = 0.0,
    val nextIncomeDate: LocalDate? = null
)