package com.weljak.splittermobile.data.repository.datasource.expense

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.data.model.expense.ExpenseCreationForm
import com.weljak.splittermobile.data.model.expense.ExpenseStatus
import com.weljak.splittermobile.data.model.expense.ExpenseType
import retrofit2.Response

interface ExpenseRemoteDataSource {
    suspend fun createExpense(token: String, expenseCreationForm: ExpenseCreationForm): Response<SplitterApiResponse<Expense>>
    suspend fun settleExpense(token: String, expenseId: String): Response<SplitterApiResponse<Expense>>
    suspend fun deleteExpense(token: String, expenseId: String): Response<SplitterApiResponse<Void>>
    suspend fun getExpensesByCriteria(token: String, paidBy: String, type: ExpenseType ?= null, status: ExpenseStatus ?= null): Response<SplitterApiResponse<List<Expense>>>
    suspend fun getExpensesByGroupName(token: String, groupName: String): Response<SplitterApiResponse<List<Expense>>>
    suspend fun getCurrentUserUnsettledExpenses(token: String): Response<SplitterApiResponse<List<Expense>>>
}