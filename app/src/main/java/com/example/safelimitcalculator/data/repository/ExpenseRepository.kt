package com.example.safelimitcalculator.data.repository

import com.example.safelimitcalculator.data.dao.ExpenseDao
import kotlinx.coroutines.flow.map
import com.example.safelimitcalculator.data.entity.toDomain
import com.example.safelimitcalculator.data.entity.toEntity
import com.example.safelimitcalculator.data.model.Expense
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {

    val allExpenses: Flow<List<Expense>> =
        expenseDao.getAllExpenses().map { list -> list.map { it.toDomain() } }

    suspend fun getExpenseById(id: Long): Expense? {
        return expenseDao.getExpenseById(id)?.toDomain()
    }

    suspend fun getExpensesForToday(): List<Expense> {
        val today = LocalDate.now()
        return expenseDao.getExpensesBetween(today, today).map { it.toDomain() }
    }

    suspend fun getTodayTotal(): Double {
        val todayExpenses = getExpensesForToday()
        return todayExpenses.sumOf { it.amount }
    }

    suspend fun insert(expense: Expense) {
        expenseDao.insertExpense(expense.toEntity())
    }

    suspend fun getSafeLimit(): Double {
        return 100.0
    }

    suspend fun update(expense: Expense) {
        expenseDao.updateExpense(expense.toEntity())
    }

    suspend fun delete(expense: Expense) {
        expenseDao.deleteExpense(expense.toEntity())
    }

    suspend fun clearAll() {
        expenseDao.clearAll()
    }
}