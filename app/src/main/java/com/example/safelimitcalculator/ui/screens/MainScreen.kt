package com.example.safelimitcalculator.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.safelimitcalculator.ui.navigation.BottomNavItem
import com.example.safelimitcalculator.ui.navigation.BottomNavigationBar
import com.example.safelimitcalculator.ui.viewmodel.ExpensesViewModel
import com.example.safelimitcalculator.ui.viewmodel.HomeViewModel
import com.example.safelimitcalculator.ui.viewmodel.PaymentsViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import com.example.safelimitcalculator.R

@Composable
fun MainScreen() {
    val navController = rememberNavController()

    val viewModel: HomeViewModel = koinViewModel()
    val viewModelPayment: PaymentsViewModel = koinViewModel()
    val viewModelExpenses: ExpensesViewModel = koinViewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val hideBottomBarScreens = listOf(
        stringResource(R.string.preloader),
        stringResource(R.string.onboarding_1),
        stringResource(R.string.onboarding_2),
        stringResource(R.string.add_edit_expense),
        stringResource(R.string.add_edit_payment),
        stringResource(R.string.payment_detail)
    )

    Scaffold(
        bottomBar = {
            if (currentRoute !in hideBottomBarScreens) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "preloader",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("preloader") { PreloaderScreen(navController) }
            composable("onboarding_1") { OnboardingScreen1(navController) }
            composable("onboarding_2") { OnboardingScreen2(navController) }
            composable(BottomNavItem.Home.route) {
                HomeScreen(
                    navController,
                    viewModel, viewModelExpenses, viewModelPayment
                )
            }
            composable(BottomNavItem.Expenses.route) { ExpensesScreen(navController) }
            composable(BottomNavItem.Payments.route) { PaymentsScreen(navController) }
            composable(BottomNavItem.Analytics.route) { AnalyticsScreen() }
            composable(BottomNavItem.Settings.route) { SettingsScreen() }
            composable(
                route = "add_edit_expense?expenseId={expenseId}",
                arguments = listOf(
                    navArgument("expenseId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) {
                AddEditExpenseScreen(navController)
            }
            composable(
                route = "payment_detail/{paymentId}",
                arguments = listOf(
                    navArgument("paymentId") {
                        type = NavType.LongType
                    }
                )
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getLong("paymentId") ?: 0L
                PaymentDetailScreen(
                    paymentId = id,
                    navController = navController
                )
            }
            composable(
                route = "add_edit_payment?paymentId={paymentId}",
                arguments = listOf(
                    navArgument("paymentId") {
                        type = NavType.LongType
                        defaultValue = -1L
                    }
                )
            ) { backStackEntry ->

                val id = backStackEntry.arguments?.getLong("paymentId") ?: -1L

                AddEditPaymentScreen(
                    paymentId = id,
                    navController = navController
                )
            }
        }
    }
}
