package com.example.safelimitcalculator.notifications

import android.content.Context
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

object FinanceNotificationManager {

    private var lastLimitWarningDay: LocalDate? = null

    fun onExpenseAdded(
        context: Context,
        dailyTotal: Double,
        safeLimit: Double
    ) {

        val today = LocalDate.now()

        if (dailyTotal > safeLimit && lastLimitWarningDay != today) {

            NotificationService.showNotification(
                context,
                "Daily Limit Exceeded",
                "You have exceeded your safe daily spending limit.",
                "ExpensesScreen"
            )

            lastLimitWarningDay = today
        }
    }

    fun checkUpcomingPayment(
        context: Context,
        paymentName: String,
        paymentDate: LocalDate
    ) {

        val days = ChronoUnit.DAYS.between(LocalDate.now(), paymentDate)

        if (days in 0..2) {

            NotificationService.showNotification(
                context,
                "Upcoming Payment Due",
                "A mandatory payment is due soon.",
                "PaymentsScreen"
            )
        }
    }

    fun checkIncomeReminder(
        context: Context,
        incomeAmount: Double,
        incomeDate: LocalDate
    ) {

        val days = ChronoUnit.DAYS.between(LocalDate.now(), incomeDate)

        if (days in 0..2) {

            NotificationService.showNotification(
                context,
                "Income Expected Soon",
                "Your next income is expected in 2 days.",
                "HomeScreen"
            )
        }
    }
}