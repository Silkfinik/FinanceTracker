package com.silkfinik.financetracker.domain.repository

import com.silkfinik.financetracker.domain.model.transaction.Transaction
import kotlinx.coroutines.flow.Flow
import kotlin.time.Instant
import kotlin.uuid.Uuid

interface TransactionRepository {
    // Получение списка транзакций с опциональными фильтрами
    // Если параметры null, возвращаются все данные
    fun getTransactions (
        accountId: Uuid? = null,
        categoryId: Uuid? = null,
        startDate: Instant? = null,
        endDate: Instant? = null
    ): Flow <List<Transaction>>

    // Запрос транзакции по ID
    suspend fun getTransactionById(id: Uuid): Transaction?

    // Сохранение транзакции
    suspend fun saveTransaction (transaction: Transaction)

    // Жесткое удаление транзакци
    suspend fun deleteTransaction (id: Uuid)
}