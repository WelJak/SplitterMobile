package com.weljak.splittermobile.presentation.viewmodel.expense

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.data.model.expense.ExpenseCreationForm
import com.weljak.splittermobile.data.model.expense.ExpenseStatus
import com.weljak.splittermobile.data.model.expense.ExpenseType
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.usecase.expense.*
import com.weljak.splittermobile.presentation.util.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class ExpenseViewModel(
    private val app: Application,
    private val createExpenseUseCase: CreateExpenseUseCase,
    private val deleteExpenseUseCase: DeleteExpenseUseCase,
    private val getCurrentUserUnsettledExpensesUseCase: GetCurrentUserUnsettledExpensesUseCase,
    private val getExpensesByCriteriaUseCase: GetExpensesByCriteriaUseCase,
    private val getExpensesByGroupNameUseCase: GetExpensesByGroupNameUseCase,
    private val settleExpenseUseCase: SettleExpenseUseCase
) : AndroidViewModel(app) {
    private val _createExpenseResponse = MutableLiveData<Resource<SplitterApiResponse<Expense>>>()
    val createExpenseResponse: LiveData<Resource<SplitterApiResponse<Expense>>>
        get() = _createExpenseResponse

    fun createExpense(token: String, expenseCreationForm: ExpenseCreationForm) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (ConnectionUtils.isNetworkAvailable(app)) {
                    val response = createExpenseUseCase.execute(token, expenseCreationForm)
                    _createExpenseResponse.postValue(response)
                } else {
                    _createExpenseResponse.postValue(Resource.Error("No internet connection"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _createExpenseResponse.postValue(Resource.Error(e.message.toString()))
            }
        }

    private val _deleteExpenseResponse = MutableLiveData<Resource<SplitterApiResponse<Void>>>()
    val deleteExpenseResponse: LiveData<Resource<SplitterApiResponse<Void>>>
        get() = _deleteExpenseResponse

    fun deleteExpense(token: String, expenseId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = deleteExpenseUseCase.execute(token, expenseId)
                _deleteExpenseResponse.postValue(response)
            } else {
                _deleteExpenseResponse.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _deleteExpenseResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _unsettledExpenses = MutableLiveData<Resource<SplitterApiResponse<List<Expense>>>>()
    val unsettledExpenses: LiveData<Resource<SplitterApiResponse<List<Expense>>>>
        get() = _unsettledExpenses

    fun getCurrentUserUnsettledExpenses(token: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = getCurrentUserUnsettledExpensesUseCase.execute(token)
                _unsettledExpenses.postValue(response)
            } else {
                _unsettledExpenses.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _unsettledExpenses.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _expenses = MutableLiveData<Resource<SplitterApiResponse<List<Expense>>>>()
    val expenses: LiveData<Resource<SplitterApiResponse<List<Expense>>>>
        get() = _expenses

    fun getExpensesByCriteria(
        token: String,
        paidBy: String,
        type: ExpenseType? = null,
        status: ExpenseStatus? = null
    ) =
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (ConnectionUtils.isNetworkAvailable(app)) {
                    val response = getExpensesByCriteriaUseCase.execute(token, paidBy, type, status)
                    _expenses.postValue(response)
                } else {
                    _expenses.postValue(Resource.Error("No internet connection"))
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _expenses.postValue(Resource.Error(e.message.toString()))
            }
        }

    private val _groupExpenses = MutableLiveData<Resource<SplitterApiResponse<List<Expense>>>>()
    val groupExpenses: LiveData<Resource<SplitterApiResponse<List<Expense>>>>
        get() = _groupExpenses

    fun getExpensesByGroupName(token: String, groupName: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = getExpensesByGroupNameUseCase.execute(token, groupName)
                _groupExpenses.postValue(response)
            } else {
                _groupExpenses.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _groupExpenses.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _settleExpenseResponse = MutableLiveData<Resource<SplitterApiResponse<Expense>>>()
    private val settleExpenseResponse: LiveData<Resource<SplitterApiResponse<Expense>>>
        get() = _settleExpenseResponse

    fun settleExpense(token: String, expenseId: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = settleExpenseUseCase.execute(token, expenseId)
                _settleExpenseResponse.postValue(response)
            } else {
                _settleExpenseResponse.postValue(Resource.Error("No internet connection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _settleExpenseResponse.postValue(Resource.Error(e.message.toString()))
        }
    }
}
