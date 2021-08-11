package com.weljak.splittermobile.domain.usecase.expense

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.expense.ExpenseRepository

class GetCurrentUserUnsettledExpensesUseCase(private val expenseRepository: ExpenseRepository) {
    suspend fun execute(token: String): Resource<SplitterApiResponse<List<Expense>>> {
        return expenseRepository.getCurrentUserUnsettledExpenses(token)
    }
}