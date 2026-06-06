package com.silkfinik.financetracker.domain.model.account

import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Account @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid = Uuid.generateV7(), // ID счета
    val name: String, // Пользовательское название
    val type: AccountType, // Тип счета
    val currencyCode: String, // Трехбуквенный код валюты
    val balance: Long, // Текущий баланс в минорных единицах (копейки, центы)
    val colorHex: String, // Цветовой код для UI
    val createdAt: Instant = Clock.System.now(), // Дата и время создания счета
    val isArchived: Boolean = false // Флаг мягкого удаления
) {
    init {
        require(name.isNotBlank()) { "Account name cannot be blank" }
        require(currencyCode.length == 3) { "Currency code must be exactly 3 characters long" }
        require(colorHex.startsWith("#")) { "Color code must start with a '#' symbol" }

        if (type == AccountType.CASH || type == AccountType.SAVINGS) {
            require(balance >= 0) { "Cash or savings accounts cannot have a negative balance" }
        }
    }
}
