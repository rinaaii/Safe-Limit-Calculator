package com.example.safelimitcalculator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.data.entity.PaymentEntity
import com.example.safelimitcalculator.data.repository.PaymentRepository
import com.example.safelimitcalculator.ui.model.PaymentsUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentsViewModel(
    private val repository: PaymentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentsUiState())
    val uiState: StateFlow<PaymentsUiState> = _uiState

    init {
        observePayments()
    }

    private fun observePayments() {
        viewModelScope.launch {
            repository.allPayments.collect { list ->
                _uiState.value = PaymentsUiState(
                    isLoading = false,
                    payments = list
                )
            }
        }
    }

    fun delete(payment: PaymentEntity) {
        viewModelScope.launch {
            repository.delete(payment)
        }
    }
}