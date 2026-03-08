package com.example.safelimitcalculator.ui.viewmodel

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.data.entity.ExpenseEntity
import com.example.safelimitcalculator.data.repository.ExpenseRepository
import com.example.safelimitcalculator.notifications.FinanceNotificationManager
import com.example.safelimitcalculator.ui.model.AddEditExpenseUiState
import com.example.safelimitcalculator.utils.InputValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddEditExpenseViewModel(
    private val repository: ExpenseRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val expenseId: Long =
        savedStateHandle["expenseId"] ?: -1L

    private val _uiState = MutableStateFlow(AddEditExpenseUiState())
    val uiState: StateFlow<AddEditExpenseUiState> = _uiState

    init {
        if (expenseId != -1L) {
            loadExpense()
        }
    }

    private fun loadExpense() {
        viewModelScope.launch {
            val expense = repository.getExpenseById(expenseId)
            expense?.let {
                _uiState.value = AddEditExpenseUiState(
                    amount = it.amount.toString(),
                    category = it.category,
                    date = it.date,
                    isEditMode = true
                )
            }
        }
    }

    fun onAmountChange(value: String) {
        _uiState.value = _uiState.value.copy(amount = value)
    }

    fun onCategoryChange(value: String) {
        _uiState.value = _uiState.value.copy(category = value)
    }

    fun save(context: Context, onSuccess: () -> Unit) {
        val state = _uiState.value

        val amountError = InputValidator.validateNumeric(state.amount)
        if (amountError != null) {
            _uiState.value = state.copy(error = amountError)
            return
        }

        val categoryError = InputValidator.validateText(state.category, "Category")
        if (categoryError != null) {
            _uiState.value = state.copy(error = categoryError)
            return
        }

        viewModelScope.launch {

            val entity = ExpenseEntity(
                id = if (_uiState.value.isEditMode) expenseId else 0,
                amount = state.amount.toDouble(),
                category = state.category,
                date = state.date
            )

            if (_uiState.value.isEditMode) {

                repository.update(entity)

            } else {

                repository.insert(entity)

                val dailyTotal = repository.getTodayTotal()
                val safeLimit = repository.getSafeLimit()

                FinanceNotificationManager.onExpenseAdded(
                    context = context,
                    dailyTotal = dailyTotal,
                    safeLimit = safeLimit
                )
            }

            onSuccess()
        }
    }
}