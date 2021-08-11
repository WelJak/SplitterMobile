package com.weljak.splittermobile.domain.usecase.expense

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.expense.ExpenseRepository

class DeleteExpenseUseCase(private val expenseRepository: ExpenseRepository) {
    suspend fun execute(token: String, expenseId: String): Resource<SplitterApiResponse<Void>> {
        return expenseRepository.deleteExpense(token, expenseId)
    }
}