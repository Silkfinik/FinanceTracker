package com.silkfinik.financetracker.domain.repository

import com.silkfinik.financetracker.domain.model.account.Account
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

interface AccountRepository {
    // Получение списка всех счетов
    fun getAllAccounts(includeArchived: Boolean = false): Flow<List<Account>>

    // Запрос счета по ID
    suspend fun getAccountById(id: Uuid): Account?

    // Создание нового или обновление существующего счета
    suspend fun saveAccount(account: Account)

    // Мягкое удаление счета
    suspend fun archiveAccount(id: Uuid)

    // Точечное обновление баланса
    suspend fun updateBalance(id: Uuid, newBalance: Long)
}