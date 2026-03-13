package com.example.safelimitcalculator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.safelimitcalculator.data.model.Expense
import org.threeten.bp.LocalDate

@Entity(tableName = "expenses")
data class ExpenseEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val amount: Double,
    val category: String,
    val date: LocalDate
)

fun ExpenseEntity.toDomain(): Expense {
    return Expense(
        id = this.id,
        amount = this.amount,
        category = this.category,
        date = this.date
    )
}

fun Expense.toEntity(): ExpenseEntity = ExpenseEntity(
    id = this.id,
    amount = this.amount,
    category = this.category,
    date = this.date
)