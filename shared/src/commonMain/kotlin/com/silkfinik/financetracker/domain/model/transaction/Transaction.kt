package com.silkfinik.financetracker.domain.model.transaction

import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Transaction @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid = Uuid.generateV7(), // ID операции
    val type: TransactionType, // Тип операции
    val amount: Long, // Сумма операции в минорных единицах (копейки, центы)
    val timestamp: Instant = Clock.System.now(), // Точное время совершения операции
    val accountId: Uuid, // ID счета к которому привязана операция
    val categoryId: Uuid?, // ID категории. null, если тип TRANSFER
    val destinationAccountId: Uuid?, // ID счета зачисления. Только если type == TRANSFER
    val status: TransactionStatus = TransactionStatus.COMPLETED, // Статус операции
    val note: String = "" // Текстовый комментарий пользователя
) {
    init {
        when (type) {
            TransactionType.TRANSFER -> {
                require(destinationAccountId != null) { "A transfer transaction must specify a destination account (destinationAccountId)" }
                require(categoryId == null) { "A transfer transaction must not have a category" }
            }
            else -> { // INCOME, EXPENSE
                require(destinationAccountId == null) { "Only transfer transactions can specify a destination account" }
                require(categoryId != null) { "Income and expense transactions must have a category" }
            }
        }
    }
}