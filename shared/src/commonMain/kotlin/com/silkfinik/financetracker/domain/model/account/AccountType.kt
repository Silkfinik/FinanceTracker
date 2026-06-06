package com.silkfinik.financetracker.domain.model.account

// Тип счета
enum class AccountType {
    CASH, // Наличные
    BANK_ACCOUNT, // Дебетовая карта / Банковский счет
    CREDIT_CARD, // Кредитная карта (может уходить в минус)
    SAVINGS // Сберегательный счет / Вклад
}