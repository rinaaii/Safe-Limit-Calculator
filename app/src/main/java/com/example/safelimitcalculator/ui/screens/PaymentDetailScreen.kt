package com.example.safelimitcalculator.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import com.example.safelimitcalculator.R
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.ui.viewmodel.PaymentDetailViewModel
import org.koin.androidx.compose.koinViewModel
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.safelimitcalculator.utils.formatDate

@Composable
fun PaymentDetailScreen(
    paymentId: Long,
    navController: NavController,
    viewModel: PaymentDetailViewModel = koinViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val shapes = LocalAppTheme.shapes
    val dimens = LocalAppTheme.dimens

    LaunchedEffect(Unit) {
        viewModel.loadPayment(paymentId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .statusBarsPadding()
            .padding(horizontal = dimens.screenPadding)
            .padding(bottom = dimens.screenPadding)
    ) {
        if (uiState.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = colors.primaryAccent
            )
            return@Box
        }

        uiState.payment?.let { payment ->
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(dimens.md)
            ) {
                Text(
                    text = payment.name,
                    style = typography.headline1,
                    color = colors.textPrimary
                )

                Text(
                    text = "$${"%.2f".format(payment.amount)}",
                    style = typography.headline2,
                    color = colors.primaryAccent
                )

                Text(
                    text = "Due: ${formatDate(payment.date)}",
                    style = typography.body,
                    color = colors.textSecondary
                )

                Text(
                    text = stringResource(R.string.description),
                    style = typography.headline2,
                    color = colors.textPrimary
                )

                Card(
                    shape = shapes.md,
                    colors = CardDefaults.cardColors(
                        containerColor = colors.surface
                    ),
                    border = BorderStroke(1.dp, colors.divider),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = payment.description.ifBlank { stringResource(R.string.no_description_provided) },
                        modifier = Modifier.padding(dimens.md),
                        style = typography.body,
                        color = if (payment.description.isBlank()) colors.textSecondary else colors.textPrimary
                    )
                }

                Button(
                    onClick = {
                        viewModel.delete(payment)
                        navController.popBackStack()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = colors.error.copy(alpha = 0.1f),
                        contentColor = colors.error
                    ),
                    border = BorderStroke(1.dp, colors.error),
                    shape = shapes.md,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(stringResource(R.string.delete_payment), style = typography.headline2)
                }
            }

            FloatingActionButton(
                onClick = {
                    navController.navigate("add_edit_payment?paymentId=${payment.id}")
                },
                containerColor = colors.primaryAccent,
                contentColor = colors.background,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = dimens.xl * 2)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = null,
                    tint = colors.background
                )
            }
        }
    }
}

