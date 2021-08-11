package com.weljak.splittermobile.data.repository.datasource.expense

import com.weljak.splittermobile.data.api.SplitterApiService
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.data.model.expense.ExpenseCreationForm
import com.weljak.splittermobile.data.model.expense.ExpenseStatus
import com.weljak.splittermobile.data.model.expense.ExpenseType
import retrofit2.Response

class RetrofitExpenseRemoteDataSource(private val splitterApiService: SplitterApiService): ExpenseRemoteDataSource {
    override suspend fun createExpense(
        token: String,
        expenseCreationForm: ExpenseCreationForm
    ): Response<SplitterApiResponse<Expense>> {
        return splitterApiService.createExpense(token, expenseCreationForm)
    }

    override suspend fun settleExpense(
        token: String,
        expenseId: String
    ): Response<SplitterApiResponse<Expense>> {
        return splitterApiService.settleExpense(token, expenseId)
    }

    override suspend fun deleteExpense(
        token: String,
        expenseId: String
    ): Response<SplitterApiResponse<Void>> {
        return splitterApiService.deleteExpense(token, expenseId)
    }

    override suspend fun getExpensesByCriteria(
        token: String,
        paidBy: String,
        type: ExpenseType?,
        status: ExpenseStatus?
    ): Response<SplitterApiResponse<List<Expense>>> {
        return splitterApiService.getExpensesByCriteria(token, paidBy, type, status)
    }

    override suspend fun getExpensesByGroupName(
        token: String,
        groupName: String
    ): Response<SplitterApiResponse<List<Expense>>> {
        return splitterApiService.getExpensesByGroupName(token, groupName)
    }

    override suspend fun getCurrentUserUnsettledExpenses(token: String): Response<SplitterApiResponse<List<Expense>>> {
        return splitterApiService.getCurrentUserUnsettledExpenses(token)
    }
}