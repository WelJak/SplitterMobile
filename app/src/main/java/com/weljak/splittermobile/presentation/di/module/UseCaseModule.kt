package com.weljak.splittermobile.presentation.di.module

import com.weljak.splittermobile.domain.repository.friend.FriendRepository
import com.weljak.splittermobile.domain.repository.user.UserRepository
import com.weljak.splittermobile.domain.usecase.friend.GetCurrentUserFriendListUseCase
import com.weljak.splittermobile.domain.usecase.user.AuthenticateUserUseCase
import com.weljak.splittermobile.domain.usecase.user.GetUserDetailsUseCase
import com.weljak.splittermobile.domain.usecase.user.RegisterUserUseCase
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

    @Singleton
    @Provides
    fun provideGetCurrentUserFriendListUseCase(friendRepository: FriendRepository): GetCurrentUserFriendListUseCase {
        return GetCurrentUserFriendListUseCase(friendRepository)
    }
}