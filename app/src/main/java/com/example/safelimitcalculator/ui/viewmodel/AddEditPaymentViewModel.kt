package com.example.safelimitcalculator.ui.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.safelimitcalculator.data.entity.PaymentEntity
import com.example.safelimitcalculator.data.model.Payment
import com.example.safelimitcalculator.data.repository.PaymentRepository
import com.example.safelimitcalculator.notifications.FinanceNotificationManager
import com.example.safelimitcalculator.ui.model.AddEditPaymentUiState
import com.example.safelimitcalculator.utils.InputValidator
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate
import org.threeten.bp.temporal.ChronoUnit

class AddEditPaymentViewModel(
    private val repository: PaymentRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AddEditPaymentUiState())
    val uiState: StateFlow<AddEditPaymentUiState> = _uiState

    private var currentId: Long? = null

    fun loadPayment(id: Long) {
        if (id == -1L) return

        viewModelScope.launch {
            currentId = id
            val payment = repository.getById(id) ?: return@launch

            _uiState.value = AddEditPaymentUiState(
                name = payment.name,
                amount = payment.amount.toString(),
                description = payment.description,
                date = payment.date
            )
        }
    }

    fun onNameChange(value: String) {
        _uiState.update { it.copy(name = value) }
    }

    fun onAmountChange(value: String) {
        _uiState.update { it.copy(amount = value) }
    }

    fun onDescriptionChange(value: String) {
        _uiState.update { it.copy(description = value) }
    }

    fun onDateChange(newDate: LocalDate) {
        _uiState.update { it.copy(date = newDate) }
    }

    fun save(context: Context, onSuccess: () -> Unit) {
        val state = _uiState.value

        val nameError = InputValidator.validateText(state.name, "Name")
        if (nameError != null) {
            _uiState.update { it.copy(error = nameError) }
            return
        }

        val amountError = InputValidator.validateNumeric(state.amount)
        if (amountError != null) {
            _uiState.update { it.copy(error = amountError) }
            return
        }

        if (state.description.length > 100) {
            _uiState.update { it.copy(error = "Description must be max 100 chars") }
            return
        }

        viewModelScope.launch {
            val entity = Payment(
                id = currentId ?: 0,
                name = state.name,
                amount = state.amount.toDouble(),
                description = state.description,
                date = state.date
            )

            if (currentId == null) {
                repository.insert(entity)

                val now = LocalDate.now()
                val paymentDate = entity.date
                val daysUntilPayment = ChronoUnit.DAYS.between(now, paymentDate)

                if (daysUntilPayment < 2) {
                    FinanceNotificationManager.checkUpcomingPayment(
                        context = context,
                        paymentName = entity.name,
                        paymentDate = entity.date
                    )
                }
            } else {
                repository.update(entity)
            }

            onSuccess()
        }
    }
}