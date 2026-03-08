package com.example.safelimitcalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.safelimitcalculator.R
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.ui.LimitIndicator
import com.example.safelimitcalculator.ui.components.RecentExpenses
import com.example.safelimitcalculator.ui.components.UpcomingPayments
import com.example.safelimitcalculator.ui.navigation.Screen
import com.example.safelimitcalculator.ui.viewmodel.ExpensesViewModel
import com.example.safelimitcalculator.ui.viewmodel.HomeViewModel
import com.example.safelimitcalculator.ui.viewmodel.PaymentsViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel,
    viewModelExpense: ExpensesViewModel,
    viewModelPayment: PaymentsViewModel
) {
    val uiState by viewModel.uiState.collectAsState()
    val dimens = LocalAppTheme.dimens
    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val scroll = rememberScrollState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .padding(horizontal = dimens.screenPadding)
            .statusBarsPadding()
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colors.primaryAccent
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scroll),
                verticalArrangement = Arrangement.spacedBy(dimens.lg)
            ) {
                Text(
                    text = stringResource(R.string.home),
                    style = typography.headline1,
                    color = colors.textPrimary
                )

                LimitIndicator(safeLimit = uiState.safeLimit)

                Column(verticalArrangement = Arrangement.spacedBy(dimens.md)) {
                    Text(
                        text = stringResource(R.string.today_s_expenses),
                        style = typography.headline2,
                        color = colors.textPrimary
                    )

                    RecentExpenses(
                        expenses = uiState.todayExpenses,
                        onClickExpense = { expense ->
                            navController.navigate(Screen.AddEditExpense.createRoute(expense.id))
                        },
                        onDeleteExpense = { expense ->
                            viewModelExpense.deleteExpense(expense)
                        }
                    )
                }

                Column(verticalArrangement = Arrangement.spacedBy(dimens.md)) {
                    Text(
                        text = stringResource(R.string.upcoming_payments),
                        style = typography.headline2,
                        color = colors.textPrimary
                    )

                    UpcomingPayments(
                        payments = uiState.upcomingPayments,
                        onClickPayment = { payment ->
                            navController.navigate("payment_detail/${payment.id}")
                        },
                        onDeletePayment = { payment ->
                            viewModelPayment.delete(payment)
                        },
                    )
                }

                Spacer(modifier = Modifier.height(dimens.xl))
            }
        }
    }
}
