package com.example.safelimitcalculator.data.repository

import com.example.safelimitcalculator.data.dao.ExpenseDao
import com.example.safelimitcalculator.data.entity.ExpenseEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

class ExpenseRepository(
    private val expenseDao: ExpenseDao
) {

    val allExpenses: Flow<List<ExpenseEntity>> =
        expenseDao.getAllExpenses()

    suspend fun getExpenseById(id: Long): ExpenseEntity? {
        return expenseDao.getExpenseById(id)
    }

    suspend fun getExpensesForToday(): List<ExpenseEntity> {
        val today = LocalDate.now()
        return expenseDao.getExpensesBetween(today, today)
    }

    suspend fun getTodayTotal(): Double {
        val todayExpenses = getExpensesForToday()
        return todayExpenses.sumOf { it.amount }
    }

    suspend fun insert(expense: ExpenseEntity) {
        expenseDao.insertExpense(expense)
    }

    suspend fun getSafeLimit(): Double {
        return 100.0
    }

    suspend fun update(expense: ExpenseEntity) {
        expenseDao.updateExpense(expense)
    }

    suspend fun delete(expense: ExpenseEntity) {
        expenseDao.deleteExpense(expense)
    }

    suspend fun clearAll() {
        expenseDao.clearAll()
    }
}