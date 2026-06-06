package com.silkfinik.financetracker.domain.usecase.account

import com.silkfinik.financetracker.domain.error.BusinessError
import com.silkfinik.financetracker.domain.error.DomainError
import com.silkfinik.financetracker.domain.error.Result
import com.silkfinik.financetracker.domain.error.StorageError
import com.silkfinik.financetracker.domain.model.transaction.Transaction
import com.silkfinik.financetracker.domain.model.transaction.TransactionType
import com.silkfinik.financetracker.domain.repository.AccountRepository
import com.silkfinik.financetracker.domain.repository.TransactionRepository
import kotlin.math.abs
import kotlin.uuid.Uuid

class AdjustAccountBalanceUseCase(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository
) {
    suspend operator fun invoke(accountId: Uuid, actualBalance: Long): Result<Unit, DomainError> {
        return try {
            val account = accountRepository.getAccountById(accountId)
                ?: return Result.Error(BusinessError.AccountNotFound)

            val difference = actualBalance - account.balance
            if (difference == 0L) return Result.Success(Unit)

            val type = if (difference > 0) TransactionType.INCOME else TransactionType.EXPENSE

            val correctionTransaction = Transaction(
                type = type,
                amount = abs(difference),
                accountId = accountId,
                categoryId = null,
                destinationAccountId = null,
                note = "Корректировка баланса"
            )

            // В реализации репозиториев этот процесс
            // должен быть обернут в транзакцию базы данных
            transactionRepository.saveTransaction(correctionTransaction)
            accountRepository.updateBalance(accountId, actualBalance)

            Result.Success(Unit)
        } catch (e: IllegalArgumentException) {
            Result.Error(BusinessError.InvalidOperation(e.message ?: "Validation error during adjustment"))
        } catch (e: Exception) {
            Result.Error(StorageError.Unknown(e.message ?: "Unknown storage error"))
        }
    }
}