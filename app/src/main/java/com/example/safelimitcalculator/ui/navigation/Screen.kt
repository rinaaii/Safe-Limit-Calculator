package com.example.safelimitcalculator.ui.navigation

import com.example.safelimitcalculator.R

sealed class Screen(
    val route: String,
    val icon: Int? = null,
    val label: String? = null
) {

    object Home : Screen(
        route = "home",
        icon = R.drawable.ic_home,
        label = "Home"
    )

    object Expenses : Screen(
        route = "expenses",
        icon = R.drawable.ic_expenses,
        label = "Expenses"
    )

    object PaymentDetail :
        Screen("payment_detail/{paymentId}") {

        fun createRoute(id: Long) =
            "payment_detail/$id"
    }

    object AddEditPayment :
        Screen("add_edit_payment?paymentId={paymentId}") {

        fun createRoute(id: Long? = null): String {
            return if (id != null)
                "add_edit_payment?paymentId=$id"
            else
                "add_edit_payment"
        }
    }

    object Analytics : Screen(
        route = "analytics",
        icon = R.drawable.ic_analytics,
        label = "Analytics"
    )

    object Settings : Screen(
        route = "settings",
        icon = R.drawable.ic_settings,
        label = "Settings"
    )

    object AddEditExpense : Screen(
        route = "add_edit_expense?expenseId={expenseId}"
    ) {
        fun createRoute(id: Long? = null): String {
            return if (id != null) {
                "add_edit_expense?expenseId=$id"
            } else {
                "add_edit_expense"
            }
        }
    }
}