package com.weljak.splittermobile.data.repository.expense

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.data.model.expense.ExpenseCreationForm
import com.weljak.splittermobile.data.model.expense.ExpenseStatus
import com.weljak.splittermobile.data.model.expense.ExpenseType
import com.weljak.splittermobile.data.repository.datasource.expense.ExpenseRemoteDataSource
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.expense.ExpenseRepository

class AndroidExpenseRepository(
    private val expenseRemoteDataSource: ExpenseRemoteDataSource
    ): ExpenseRepository {
    override suspend fun createExpense(
        token: String,
        expenseCreationForm: ExpenseCreationForm
    ): Resource<SplitterApiResponse<Expense>> {
        return Resource.valueOf(expenseRemoteDataSource.createExpense(token, expenseCreationForm))
    }

    override suspend fun settleExpense(
        token: String,
        expenseId: String
    ): Resource<SplitterApiResponse<Expense>> {
        return Resource.valueOf(expenseRemoteDataSource.settleExpense(token, expenseId))
    }

    override suspend fun deleteExpense(
        token: String,
        expenseId: String
    ): Resource<SplitterApiResponse<Void>> {
        return Resource.valueOf(expenseRemoteDataSource.deleteExpense(token, expenseId))
    }

    override suspend fun getExpensesByCriteria(
        token: String,
        paidBy: String,
        type: ExpenseType?,
        status: ExpenseStatus?
    ): Resource<SplitterApiResponse<List<Expense>>> {
        return Resource.valueOf(expenseRemoteDataSource.getExpensesByCriteria(token, paidBy, type, status))
    }

    override suspend fun getExpensesByGroupName(
        token: String,
        groupName: String
    ): Resource<SplitterApiResponse<List<Expense>>> {
        return Resource.valueOf(expenseRemoteDataSource.getExpensesByGroupName(token, groupName))
    }

    override suspend fun getCurrentUserUnsettledExpenses(token: String): Resource<SplitterApiResponse<List<Expense>>> {
        return Resource.valueOf(expenseRemoteDataSource.getCurrentUserUnsettledExpenses(token))
    }
}