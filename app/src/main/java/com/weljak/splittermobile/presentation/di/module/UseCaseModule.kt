package com.weljak.splittermobile.presentation.di.module

import com.weljak.splittermobile.domain.repository.user.UserRepository
import com.weljak.splittermobile.domain.usecase.AuthenticateUserUseCase
import com.weljak.splittermobile.domain.usecase.GetUserDetailsUseCase
import com.weljak.splittermobile.domain.usecase.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideAuthenticateUserUseCase(userRepository: UserRepository): AuthenticateUserUseCase {
        return AuthenticateUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUserUseCase {
        return RegisterUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideGetUserDetailsUseCase(userRepository: UserRepository): GetUserDetailsUseCase {
        return GetUserDetailsUseCase(userRepository)
    }
}