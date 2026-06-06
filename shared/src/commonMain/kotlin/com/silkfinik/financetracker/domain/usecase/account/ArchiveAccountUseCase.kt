package com.silkfinik.financetracker.domain.usecase.account

import com.silkfinik.financetracker.domain.error.BusinessError
import com.silkfinik.financetracker.domain.error.DomainError
import com.silkfinik.financetracker.domain.error.Result
import com.silkfinik.financetracker.domain.error.StorageError
import com.silkfinik.financetracker.domain.repository.AccountRepository
import kotlin.uuid.Uuid

class ArchiveAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(id: Uuid): Result<Unit, DomainError> {
        return try {
            val account = accountRepository.getAccountById(id)
                ?: return Result.Error(BusinessError.AccountNotFound)

            if (account.balance != 0L) {
                return Result.Error(BusinessError.InvalidOperation("Account cannot be archived: balance must be zero"))
            }

            accountRepository.archiveAccount(id)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(StorageError.Unknown(e.message ?: "Unknown storage error"))
        }
    }
}