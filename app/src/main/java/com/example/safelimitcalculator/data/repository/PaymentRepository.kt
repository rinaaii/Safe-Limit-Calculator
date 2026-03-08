package com.example.safelimitcalculator.data.repository

import com.example.safelimitcalculator.data.dao.PaymentDao
import com.example.safelimitcalculator.data.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

class PaymentRepository(
    private val paymentDao: PaymentDao
) {

    val allPayments: Flow<List<PaymentEntity>> =
        paymentDao.getAllPayments()

    suspend fun getById(id: Long): PaymentEntity? {
        return paymentDao.getById(id)
    }

    suspend fun getUpcomingPayments(): List<PaymentEntity> {
        val today = LocalDate.now()
        return paymentDao.getPaymentsAfter(today)
    }

    suspend fun insert(payment: PaymentEntity) {
        paymentDao.insertPayment(payment)
    }

    suspend fun update(payment: PaymentEntity) {
        paymentDao.updatePayment(payment)
    }

    suspend fun delete(payment: PaymentEntity) {
        paymentDao.deletePayment(payment)
    }

    suspend fun clearAll() {
        paymentDao.clearAll()
    }
}