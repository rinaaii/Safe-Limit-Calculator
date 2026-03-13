package com.example.safelimitcalculator.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.safelimitcalculator.data.model.Payment
import org.threeten.bp.LocalDate

@Entity(tableName = "payments")
data class PaymentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val amount: Double,
    val date: LocalDate,
    val description: String
)

fun PaymentEntity.toDomain(): Payment = Payment(
    id = this.id,
    name = this.name,
    amount = this.amount,
    date = this.date,
    description = this.description
)

fun Payment.toEntity(): PaymentEntity = PaymentEntity(
    id = this.id,
    name = this.name,
    amount = this.amount,
    date = this.date,
    description = this.description
)