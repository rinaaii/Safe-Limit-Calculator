package com.example.safelimitcalculator.utils

import org.threeten.bp.LocalDate
import org.threeten.bp.format.DateTimeFormatter
import java.util.Locale

fun formatDate(date: LocalDate): String {
    val today = LocalDate.now()
    return when (date) {
        today -> "Today"
        today.plusDays(1) -> "Tomorrow"
        else -> {
            val formatter = DateTimeFormatter.ofPattern("d MMM", Locale.ENGLISH)
            date.format(formatter)
        }
    }
}