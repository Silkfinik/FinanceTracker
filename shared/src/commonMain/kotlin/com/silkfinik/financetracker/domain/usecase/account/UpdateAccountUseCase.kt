package com.silkfinik.financetracker.domain.usecase.account

import com.silkfinik.financetracker.domain.error.Result
import com.silkfinik.financetracker.domain.error.BusinessError
import com.silkfinik.financetracker.domain.error.DomainError
import com.silkfinik.financetracker.domain.error.StorageError
import com.silkfinik.financetracker.domain.model.account.Account
import com.silkfinik.financetracker.domain.repository.AccountRepository
import kotlin.uuid.Uuid

class UpdateAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(
        id: Uuid,
        newName: String,
        newColorHex: String
    ): Result<Unit, DomainError> {
        return try {
            val account = accountRepository.getAccountById(id)
                ?: return Result.Error(BusinessError.AccountNotFound)

            // Чтобы отработал init блок валидации
            val updatedAccount = Account(
                id = account.id,
                name = newName,
                type = account.type,
                currencyCode = account.currencyCode,
                balance = account.balance,
                colorHex = newColorHex,
                createdAt = account.createdAt,
                isArchived = account.isArchived
            )

            accountRepository.saveAccount(updatedAccount)
            Result.Success(Unit)
        } catch (e: IllegalArgumentException) {
            Result.Error(BusinessError.InvalidOperation(e.message ?: "Data validation error"))
        } catch (e: Exception) {
            Result.Error(StorageError.Unknown(e.message ?: "Unknown storage error"))
        }
    }
}