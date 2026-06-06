package com.silkfinik.financetracker.domain.error

sealed interface Result<out D, out E : DomainError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : DomainError>(val error: E) : Result<Nothing, E>
}

// Расширение для обработки успешных результатов
inline fun <T, E : DomainError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    if (this is Result.Success) action(data)
    return this
}

// Расширение для обработки ошибок
inline fun <T, E : DomainError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    if (this is Result.Error) action(error)
    return this
}