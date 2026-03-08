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
import com.example.safelimitcalculator.data.entity.ExpenseEntity
import com.example.safelimitcalculator.ui.theme.LocalAppTheme

@Composable
fun RecentExpenses(
    expenses: List<ExpenseEntity>,
    onClickExpense: (ExpenseEntity) -> Unit,
    onDeleteExpense: (ExpenseEntity) -> Unit
) {
    val dimens = LocalAppTheme.dimens

    if (expenses.isEmpty()) {
        Text(
            stringResource(R.string.no_expenses_today),
            style = LocalAppTheme.typography.body,
            color = LocalAppTheme.colors.textSecondary,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    } else {
       Column(verticalArrangement = Arrangement.spacedBy(dimens.sm)) {
            expenses.forEach { expense ->
                ExpenseListItem(
                    expense = expense,
                    onClick = { onClickExpense(expense) },
                    onDelete = { onDeleteExpense(expense) }
                )
            }
        }
    }
}