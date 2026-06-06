package com.silkfinik.financetracker.domain.model.budget

import kotlin.time.Clock
import kotlin.time.Instant
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

data class Budget @OptIn(ExperimentalUuidApi::class) constructor(
    val id: Uuid = Uuid.generateV7(), // ID бюджета
    val categoryId: Uuid, // ID категории, на которую устанавливается лимит
    val amountLimit: Long, // Максимальная сумма в минорных единицах, которую разрешено потратить.
    val period: BudgetPeriod, // Цикл обновления бюджета
    val startDate: Instant = Clock.System.now() // Дата начала действия бюджета
) {
    init {
        require(amountLimit > 0) { "Budget limit must be strictly greater than zero" }
    }
}
