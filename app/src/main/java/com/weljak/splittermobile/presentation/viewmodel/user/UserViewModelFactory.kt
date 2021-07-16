package com.weljak.splittermobile.presentation.viewmodel.user

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weljak.splittermobile.domain.usecase.AuthenticateUserUseCase
import com.weljak.splittermobile.domain.usecase.GetUserDetailsUseCase
import com.weljak.splittermobile.domain.usecase.RegisterUserUseCase

class UserViewModelFactory(
    private val app:Application,
    private val authenticateUserUseCase: AuthenticateUserUseCase,
    private val registerUserUseCase: RegisterUserUseCase,
    private val getUserDetailsUseCase: GetUserDetailsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(
            app,
            authenticateUserUseCase,
            registerUserUseCase,
            getUserDetailsUseCase
        ) as T
    }

}