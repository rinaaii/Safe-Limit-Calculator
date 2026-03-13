package com.example.safelimitcalculator.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.data.entity.PaymentEntity
import com.example.safelimitcalculator.data.model.Payment
import com.example.safelimitcalculator.data.repository.PaymentRepository
import com.example.safelimitcalculator.ui.model.PaymentDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PaymentDetailViewModel(
    private val repository: PaymentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(PaymentDetailUiState())
    val uiState: StateFlow<PaymentDetailUiState> = _uiState

    fun loadPayment(id: Long) {
        viewModelScope.launch {
            repository.allPayments.collect { list ->
                val payment = list.find { it.id == id }
                _uiState.value = PaymentDetailUiState(
                    isLoading = false,
                    payment = payment
                )
            }
        }
    }

    fun delete(payment: Payment) {
        viewModelScope.launch {
            repository.delete(payment)
        }
    }
}