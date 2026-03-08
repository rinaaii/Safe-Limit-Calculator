package com.example.safelimitcalculator.utils

import androidx.room.TypeConverter
import org.threeten.bp.LocalDate

class DateConverters {
    @TypeConverter
    fun fromLocalDate(value: LocalDate?): String? {
        return value?.toString()
    }

    @TypeConverter
    fun toLocalDate(value: String?): LocalDate? {
        return value?.let { LocalDate.parse(it) }
    }
}