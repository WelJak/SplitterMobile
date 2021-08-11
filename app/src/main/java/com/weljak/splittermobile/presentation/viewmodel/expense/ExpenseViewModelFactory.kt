package com.weljak.splittermobile.presentation.viewmodel.expense

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weljak.splittermobile.domain.usecase.expense.*

class ExpenseViewModelFactory(
    private val app: Application,
    private val createExpenseUseCase: CreateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getCurrentUserUnsettledExpensesUseCase: GetCurrentUserUnsettledExpensesUseCase,
    private val getExpensesByCriteriaUseCase: GetExpensesByCriteriaUseCase,
    private val getExpensesByGroupNameUseCase: GetExpensesByGroupNameUseCase,
    private val settleExpenseUseCase: SettleExpenseUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExpenseViewModel(
            app,
            createExpenseUseCase,
            deleteExpenseUseCase,
            getCurrentUserUnsettledExpensesUseCase,
            getExpensesByCriteriaUseCase,
            getExpensesByGroupNameUseCase,
            settleExpenseUseCase
        ) as T
    }
}