package com.silkfinik.financetracker.domain.usecase.account

import com.silkfinik.financetracker.domain.model.account.Account
import com.silkfinik.financetracker.domain.repository.AccountRepository
import kotlinx.coroutines.flow.Flow

class GetAccountsUseCase (
    private val accountRepository: AccountRepository
) {
    operator fun invoke(includeArchived: Boolean = false): Flow<List<Account>> {
        return accountRepository.getAllAccounts(includeArchived)
    }
}