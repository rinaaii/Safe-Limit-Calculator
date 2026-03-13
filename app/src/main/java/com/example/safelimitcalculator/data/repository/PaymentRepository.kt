package com.example.safelimitcalculator.data.repository

import com.example.safelimitcalculator.data.dao.PaymentDao
import com.example.safelimitcalculator.data.entity.PaymentEntity
import com.example.safelimitcalculator.data.entity.toDomain
import com.example.safelimitcalculator.data.entity.toEntity
import com.example.safelimitcalculator.data.model.Payment
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.threeten.bp.LocalDate

class PaymentRepository(
    private val paymentDao: PaymentDao
) {

    val allPayments: Flow<List<Payment>> =
        paymentDao.getAllPayments().map { list -> list.map { it.toDomain() } }

    suspend fun getById(id: Long): Payment? {
        return paymentDao.getById(id)?.toDomain()
    }

    suspend fun getUpcomingPayments(): List<Payment> {
        val today = LocalDate.now()
        return paymentDao.getPaymentsAfter(today).map { it.toDomain() }
    }

    suspend fun insert(payment: Payment) {
        paymentDao.insertPayment(payment.toEntity())
    }

    suspend fun update(payment: Payment) {
        paymentDao.updatePayment(payment.toEntity())
    }

    suspend fun delete(payment: Payment) {
        paymentDao.deletePayment(payment.toEntity())
    }

    suspend fun clearAll() {
        paymentDao.clearAll()
    }
}