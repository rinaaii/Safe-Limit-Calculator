package com.example.safelimitcalculator.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.safelimitcalculator.data.entity.PaymentEntity
import kotlinx.coroutines.flow.Flow
import org.threeten.bp.LocalDate

@Dao
interface PaymentDao {

    @Query("SELECT * FROM payments ORDER BY date ASC")
    fun getAllPayments(): Flow<List<PaymentEntity>>

    @Query("SELECT * FROM payments WHERE date >= :fromDate ORDER BY date ASC")
    suspend fun getPaymentsAfter(fromDate: LocalDate): List<PaymentEntity>

    @Query("SELECT * FROM payments WHERE id = :id LIMIT 1")
    suspend fun getById(id: Long): PaymentEntity?

    @Query("SELECT * FROM payments WHERE id = :id")
    suspend fun getPaymentById(id: Long): PaymentEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun insertPayment(payment: PaymentEntity)

    @Update
    suspend fun updatePayment(payment: PaymentEntity)

    @Delete
    suspend fun deletePayment(payment: PaymentEntity)

    @Query("DELETE FROM payments")
    suspend fun clearAll()
}