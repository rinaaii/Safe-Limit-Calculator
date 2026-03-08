package com.example.safelimitcalculator.ui.navigation

import com.example.safelimitcalculator.R

sealed class BottomNavItem(val route: String, val icon: Int, val label: String) {
    object Home : BottomNavItem("HomeScreen", R.drawable.ic_home, "Home")
    object Expenses : BottomNavItem("ExpensesScreen", R.drawable.ic_shopping_cart, "Expenses")
    object Payments : BottomNavItem("PaymentsScreen", R.drawable.ic_date_range, "Payments")
    object Analytics : BottomNavItem("AnalyticsScreen", R.drawable.ic_analytics, "Analytics")
    object Settings : BottomNavItem("SettingsScreen", R.drawable.ic_settings, "Settings")
}