package com.weljak.splittermobile.presentation.viewmodel.user

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.user.UserDetails
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.usecase.AuthenticateUserUseCase
import com.weljak.splittermobile.domain.usecase.GetUserDetailsUseCase
import com.weljak.splittermobile.domain.usecase.RegisterUserUseCase
import com.weljak.splittermobile.presentation.util.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

    fun authenticateUser(username: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        _userToken.postValue(Resource.Loading())
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val authRequest = AuthenticationRequest(username, password)
                val response = authenticateUserUseCase.execute(authRequest)
                _userToken.postValue(response)
                //Log.i("API", response.data?.payload!!.token)
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

}