package com.silkfinik.financetracker.domain.repository

import com.silkfinik.financetracker.domain.model.category.Category
import kotlinx.coroutines.flow.Flow
import kotlin.uuid.Uuid

interface CategoryRepository {
    // Получение списка всех категорий
    fun getAllCategories (includeArchived: Boolean = false): Flow<List<Category>>

    // Запрос категории по ID
    suspend fun getCategoryById(id: Uuid): Category?

    // Создание новой или обновление существующей категории
    suspend fun saveCategory(category: Category)

    // Мягкое удаление категории
    suspend fun archiveCategory(id: Uuid)
}