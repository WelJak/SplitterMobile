package com.weljak.splittermobile.presentation.di.module

import android.app.Application
import com.weljak.splittermobile.domain.usecase.AuthenticateUserUseCase
import com.weljak.splittermobile.domain.usecase.GetUserDetailsUseCase
import com.weljak.splittermobile.domain.usecase.RegisterUserUseCase
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {
    @Singleton
    @Provides
    fun provideUserViewModelFactory(
        app:Application,
        authenticateUserUseCase: AuthenticateUserUseCase,
        registerUserUseCase: RegisterUserUseCase,
        getUserDetailsUseCase: GetUserDetailsUseCase
    ): UserViewModelFactory {
        return  UserViewModelFactory(
            app,
            authenticateUserUseCase,
            registerUserUseCase,
            getUserDetailsUseCase
        )
    }
}