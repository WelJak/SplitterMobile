package com.weljak.splittermobile.data.model.expense

import java.math.BigDecimal

data class ExpenseCreationForm(
    val description: String,
    val paidBy: String,
    val amountPaidByPayer: BigDecimal,
    val totalSum: BigDecimal,
    val currency: Currency,
    val type: ExpenseType,
    val groupName: String? = null,
    val expenseBreakdown: Map<String, BigDecimal>
)
