package com.example.safelimitcalculator.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.safelimitcalculator.R
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.ui.components.PaymentListItem
import com.example.safelimitcalculator.ui.viewmodel.PaymentsViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun PaymentsScreen(
    navController: NavController,
    viewModel: PaymentsViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val dimens = LocalAppTheme.dimens

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .padding(horizontal = dimens.screenPadding)
            .padding(bottom = dimens.sm)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colors.primaryAccent
            )
        }

        if (uiState.payments.isEmpty() && !uiState.isLoading) {
            Text(
                text = stringResource(R.string.no_payments_yet),
                style = typography.body,
                color = colors.textSecondary,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Column {
            Text(
                text = stringResource(R.string.payments),
                style = typography.headline1,
                color = colors.textPrimary
            )

            Spacer(modifier = Modifier.height(dimens.lg))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(dimens.sm),
                contentPadding = PaddingValues(
                    bottom = dimens.xl * 2
                )
            ) {
                items(uiState.payments) { payment ->
                    PaymentListItem(
                        payment = payment,
                        onDelete = { viewModel.delete(payment) },
                        onClick = {
                            navController.navigate(
                                "payment_detail/${payment.id}"
                            )
                        }
                    )
                }
            }
        }

        FloatingActionButton(
            onClick = {
                navController.navigate("add_edit_payment?paymentId=-1")
            },
            containerColor = colors.primaryAccent,
            contentColor = colors.background,
            modifier = Modifier.align(Alignment.BottomEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_add),
                contentDescription = null,
                tint = colors.background
            )
        }
    }
}
