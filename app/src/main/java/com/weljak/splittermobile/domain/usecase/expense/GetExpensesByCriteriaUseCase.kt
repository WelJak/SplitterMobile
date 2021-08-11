package com.weljak.splittermobile.domain.usecase.expense

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.data.model.expense.ExpenseStatus
import com.weljak.splittermobile.data.model.expense.ExpenseType
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.expense.ExpenseRepository

class GetExpensesByCriteriaUseCase(private val expenseRepository: ExpenseRepository) {
    suspend fun execute(token: String, paidBy: String, type: ExpenseType?= null, status: ExpenseStatus?= null): Resource<SplitterApiResponse<List<Expense>>> {
        return expenseRepository.getExpensesByCriteria(token, paidBy, type, status)
    }
}