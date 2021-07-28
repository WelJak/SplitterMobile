package com.weljak.splittermobile.presentation.viewmodel.user

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.usecase.user.AuthenticateUserUseCase
import com.weljak.splittermobile.domain.usecase.user.GetUserDetailsUseCase
import com.weljak.splittermobile.domain.usecase.user.RegisterUserUseCase
import com.weljak.splittermobile.presentation.util.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class UserViewModel(
    private val app: Application,
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase
    ):AndroidViewModel(app) {

    private val _userToken: MutableLiveData<Resource<SplitterApiResponse<AuthenticationResponse>>> = MutableLiveData()
    val userToken:LiveData<Resource<SplitterApiResponse<AuthenticationResponse>>>
        get() = _userToken

    private val _userData: MutableLiveData<Resource<SplitterApiResponse<UserDetails>>> = MutableLiveData()
    val userData:LiveData<Resource<SplitterApiResponse<UserDetails>>>
        get() = _userData

    private val _registerData: MutableLiveData<Resource<SplitterApiResponse<UserDetails>>> = MutableLiveData()
    val registerData: LiveData<Resource<SplitterApiResponse<UserDetails>>>
        get() = _registerData

    fun authenticateUser(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _userToken.postValue(Resource.Loading())
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val authRequest = AuthenticationRequest(username, password)
                val response = authenticateUserUseCase.execute(authRequest)
                _userToken.postValue(response)
            } else {
                Toast.makeText(app, "No internet connection", Toast.LENGTH_LONG).show()
                _userToken.postValue(Resource.Error("No internetConnection"))
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            _userToken.postValue(Resource.Error(exception.message.toString()))
        }
    }

    fun getUserData(username: String, token: String) = viewModelScope.launch(Dispatchers.IO) {
        _userData.postValue(Resource.Loading())
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = getUserDetailsUseCase.execute(username, token)
                _userData.postValue(response)
            } else {
                Toast.makeText(app, "No internet connection", Toast.LENGTH_LONG).show()
                _userData.postValue(Resource.Error("No internetConnection"))
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            _userData.postValue(Resource.Error(exception.message.toString()))
        }
    }

    fun registerUser(username: String, email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            _registerData.postValue(Resource.Loading())
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val request = RegisterUserRequest(username, email, password)
                val response = registerUserUseCase.execute(request)
                _registerData.postValue(response)
            } else {
                Toast.makeText(app, "No internet connection", Toast.LENGTH_LONG).show()
                _registerData.postValue(Resource.Error("No internetConnection"))
            }
        } catch (exception: Exception) {
            exception.printStackTrace()
            withContext(Dispatchers.Main) {
                Toast.makeText(this@UserViewModel.app, "Error occured during registering: ${exception.message}", Toast.LENGTH_LONG).show()
                _registerData.postValue(Resource.Error(exception.message.toString()))
            }
        }
    }

}