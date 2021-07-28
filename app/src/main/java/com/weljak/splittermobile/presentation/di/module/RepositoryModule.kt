package com.weljak.splittermobile.presentation.di.module

import com.weljak.splittermobile.data.repository.datasource.friend.FriendRemoteDataSource
import com.weljak.splittermobile.data.repository.datasource.user.UserRemoteDataSource
import com.weljak.splittermobile.data.repository.friend.AndroidFriendRepository
import com.weljak.splittermobile.data.repository.user.AndroidUserRepository
import com.weljak.splittermobile.domain.repository.friend.FriendRepository
import com.weljak.splittermobile.domain.repository.user.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Singleton
    @Provides
    fun provideUserRepository(userRemoteDataSource: UserRemoteDataSource): UserRepository {
        return AndroidUserRepository(userRemoteDataSource)
    }

    @Singleton
    @Provides
    fun provideFriendRepository(friendRemoteDataSource: FriendRemoteDataSource): FriendRepository {
        return AndroidFriendRepository(friendRemoteDataSource)
    }
}