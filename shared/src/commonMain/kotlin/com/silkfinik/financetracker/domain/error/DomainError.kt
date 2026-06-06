package com.silkfinik.financetracker.domain.error

// Для всех ошибок доменного слоя
interface DomainError

// Ошибки бизнес-логики
sealed interface BusinessError : DomainError {
    data object InsufficientFunds : BusinessError // Недостаточно средств для перевода/расхода
    data object AccountNotFound : BusinessError // Счет списания или зачисления не найден
    data object BudgetLimitExceeded : BusinessError // Превышен лимит бюджета (можно использовать для ворнингов)
    data class InvalidOperation(val reason: String) : BusinessError // Универсальная ошибка логики
}

// Ошибки хранилища
sealed interface StorageError : DomainError {
    data object ItemNotFound : StorageError // Запись не найдена в БД
    data object DiskFull : StorageError // Нет места на устройстве
    data class Unknown(val message: String) : StorageError // Непредвиденный сбой SQLite
}

// Сетевые ошибки
sealed interface NetworkError : DomainError {
    data object NoInternet : NetworkError
    data object RequestTimeout : NetworkError
    data class ServerError(val code: Int) : NetworkError
}