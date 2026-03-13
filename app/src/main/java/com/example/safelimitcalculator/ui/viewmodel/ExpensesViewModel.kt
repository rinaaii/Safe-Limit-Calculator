package com.example.safelimitcalculator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.data.model.Expense
import com.example.safelimitcalculator.data.repository.ExpenseRepository
import com.example.safelimitcalculator.ui.model.ExpensesUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ExpensesViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExpensesUiState())
    val uiState: StateFlow<ExpensesUiState> = _uiState

    init {
        observeExpenses()
    }

    private fun observeExpenses() {
        viewModelScope.launch {
            repository.allExpenses.collect { list ->
                _uiState.value = ExpensesUiState(
                    isLoading = false,
                    expenses = list
                )
            }
        }
    }

    fun deleteExpense(expense: Expense) {
        viewModelScope.launch {
            repository.delete(expense)
        }
    }
}