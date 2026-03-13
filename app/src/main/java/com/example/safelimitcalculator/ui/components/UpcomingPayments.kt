package com.example.safelimitcalculator.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.safelimitcalculator.R
import com.example.safelimitcalculator.data.entity.PaymentEntity
import com.example.safelimitcalculator.data.model.Payment
import com.example.safelimitcalculator.ui.theme.LocalAppTheme

@Composable
fun UpcomingPayments(
    payments: List<Payment>,
    onClickPayment: (Payment) -> Unit,
    onDeletePayment: (Payment) -> Unit,
) {
    val dimens = LocalAppTheme.dimens

    if (payments.isEmpty()) {
        Text(
            stringResource(R.string.no_upcoming_payments),
            style = LocalAppTheme.typography.body,
            color = LocalAppTheme.colors.textSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
        Column(verticalArrangement = Arrangement.spacedBy(dimens.sm)) {
            payments.forEach { payment ->
                PaymentListItem(
                    payment = payment,
                    onDelete = { onDeletePayment(payment) },
                    onClick = { onClickPayment(payment) }
                )
            }
        }
    }
}