package com.weljak.splittermobile.domain.usecase.expense

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.data.model.expense.ExpenseCreationForm
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.expense.ExpenseRepository

class CreateExpenseUseCase(private val expenseRepository: ExpenseRepository) {
    suspend fun execute(token: String, expenseCreationForm: ExpenseCreationForm): Resource<SplitterApiResponse<Expense>> {
        return expenseRepository.createExpense(token, expenseCreationForm)
    }
}