package com.example.safelimitcalculator.utils

object InputValidator {
    private val textRegex = Regex("^[a-zA-Z0-9а-яА-Я\\s]*$")

    fun validateText(text: String, fieldName: String): String? = when {
        text.isBlank() -> "$fieldName cannot be empty"
        text.length > 100 -> "$fieldName must be max 100 chars"
        !textRegex.matches(text) -> "$fieldName: no special symbols allowed"
        else -> null
    }

    fun validateNumeric(amountStr: String): String? {
        val amount = amountStr.toDoubleOrNull()
        return when {
            amount == null || amount <= 0 -> "Enter a valid positive number"
            amount > 1_000_000 -> "Max amount is 1,000,000"
            else -> null
        }
    }
}

