package com.example.safelimitcalculator.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.safelimitcalculator.R
import com.example.safelimitcalculator.data.entity.ExpenseEntity
import com.example.safelimitcalculator.ui.theme.LocalAppTheme
import com.example.safelimitcalculator.utils.formatDate

@Composable
fun ExpenseListItem(
    expense: ExpenseEntity,
    onDelete: () -> Unit,
    onClick: () -> Unit
) {
    val colors = LocalAppTheme.colors
    val typography = LocalAppTheme.typography
    val shapes = LocalAppTheme.shapes
    val dimens = LocalAppTheme.dimens

    Card(
        shape = shapes.md,
        colors = CardDefaults.cardColors(containerColor = colors.surface),
        border = BorderStroke(1.dp, colors.divider),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(dimens.md)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(dimens.md)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(dimens.xs)
            ) {
                Text(
                    text = expense.category,
                    style = typography.headline2,
                    color = colors.textPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Text(
                    text = "$${"%.2f".format(expense.amount)}",
                    style = typography.body,
                    color = colors.primaryAccent
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(dimens.xs)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = null,
                        modifier = Modifier.size(dimens.sm),
                        tint = colors.textSecondary
                    )
                    Text(
                        text = formatDate(expense.date),
                        style = typography.caption,
                        color = colors.textSecondary
                    )
                }
            }

            IconButton(
                onClick = onDelete,
                modifier = Modifier.size(dimens.xl)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_close),
                    contentDescription = "Delete",
                    tint = colors.iconInactive,
                    modifier = Modifier.size(dimens.lg)
                )
            }
        }
    }
}