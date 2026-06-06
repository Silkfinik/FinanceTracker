package com.silkfinik.financetracker.domain.usecase.account

import com.silkfinik.financetracker.domain.error.BusinessError
import com.silkfinik.financetracker.domain.error.Result
import com.silkfinik.financetracker.domain.error.DomainError
import com.silkfinik.financetracker.domain.error.StorageError
import com.silkfinik.financetracker.domain.model.account.Account
import com.silkfinik.financetracker.domain.model.account.AccountType
import com.silkfinik.financetracker.domain.repository.AccountRepository

class CreateAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(
        name: String,
        type: AccountType,
        currencyCode: String,
        initialBalance: Long,
        colorHex: String
    ): Result<Unit, DomainError> {
        return try {
            val account = Account(
                name = name,
                type = type,
                currencyCode = currencyCode,
                balance = initialBalance,
                colorHex = colorHex
            )
            accountRepository.saveAccount(account)
            Result.Success(Unit)
        } catch (e: IllegalArgumentException) {
            Result.Error(BusinessError.InvalidOperation(e.message ?: "Data validation error"))
        } catch (e: Exception) {
            Result.Error(StorageError.Unknown(e.message ?: "Unknown storage error"))
        }
    }
}