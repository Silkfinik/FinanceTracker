package com.silkfinik.financetracker.domain.model.category

import com.silkfinik.financetracker.domain.model.transaction.TransactionType
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Category @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid = Uuid.generateV7(), // ID категории
    val name: String, // Название
    val type: TransactionType, // Тип транзакции
    val parentId: Uuid?, // ID родительской категории. null, если это категория верхнего уровня
    val iconName: String, // Идентификатор ресурса иконки
    val colorHex: String, // Цветовой код категории
    val isArchived: Boolean = false // Флаг скрытия категории.
) {
    init {
        require(name.isNotBlank()) { "Category name cannot be blank" }
        require(colorHex.startsWith("#")) { "Color code must start with a '#' symbol" }
        require(type != TransactionType.TRANSFER) { "A category cannot be applied to a TRANSFER transaction" }

        if (parentId != null) {
            require(parentId != id) { "A category cannot be its own parent" }
        }
    }
}
