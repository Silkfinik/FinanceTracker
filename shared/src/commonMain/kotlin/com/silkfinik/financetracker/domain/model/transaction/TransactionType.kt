package com.silkfinik.financetracker.domain.model.transaction

// Тип транзакции
enum class TransactionType {
    INCOME, // Доход
    EXPENSE, // Расход
    TRANSFER // Перевод между своими счетами
}