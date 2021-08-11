package com.weljak.splittermobile.domain.repository.expense

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.data.model.expense.ExpenseCreationForm
import com.weljak.splittermobile.data.model.expense.ExpenseStatus
import com.weljak.splittermobile.data.model.expense.ExpenseType
import com.weljak.splittermobile.data.util.Resource

interface ExpenseRepository {
    suspend fun createExpense(token: String, expenseCreationForm: ExpenseCreationForm): Resource<SplitterApiResponse<Expense>>
    suspend fun settleExpense(token: String, expenseId: String): Resource<SplitterApiResponse<Expense>>
    suspend fun deleteExpense(token: String, expenseId: String): Resource<SplitterApiResponse<Void>>
    suspend fun getExpensesByCriteria(token: String, paidBy: String, type: ExpenseType?= null, status: ExpenseStatus?= null): Resource<SplitterApiResponse<List<Expense>>>
    suspend fun getExpensesByGroupName(token: String, groupName: String): Resource<SplitterApiResponse<List<Expense>>>
    suspend fun getCurrentUserUnsettledExpenses(token: String): Resource<SplitterApiResponse<List<Expense>>>
}