package com.silkfinik.financetracker.domain.repository

import com.silkfinik.financetracker.domain.model.budget.Budget
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

interface BudgetRepository {
    // Получение списка активных бюджетов
    fun getActiveBudgets(): Flow<List<Budget>>

    // Запрос бюджета по ID
    suspend fun getBudgetById(id: Uuid): Budget?

    // Создание нового или обновление существующего бюджета
    suspend fun saveBudget(budget: Budget)

    // Жесткое удаление бюджета
    suspend fun deleteBudget(id: Uuid)
}