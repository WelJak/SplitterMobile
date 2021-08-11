package com.weljak.splittermobile.presentation.di.module

import com.weljak.splittermobile.data.api.SplitterApiService
import com.weljak.splittermobile.data.repository.datasource.expense.ExpenseRemoteDataSource
import com.weljak.splittermobile.data.repository.datasource.expense.RetrofitExpenseRemoteDataSource
import com.weljak.splittermobile.data.repository.datasource.friend.FriendRemoteDataSource
import com.weljak.splittermobile.data.repository.datasource.friend.RetrofitFriendRemoteDataSource
import com.weljak.splittermobile.data.repository.datasource.user.RetrofitUserRemoteDataSource
import com.weljak.splittermobile.data.repository.datasource.user.UserRemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataModule {
    @Singleton
    @Provides
    fun provideUserRemoteDataSource(splitterApiService: SplitterApiService): UserRemoteDataSource {
        return RetrofitUserRemoteDataSource(splitterApiService)
    }

    @Singleton
    @Provides
    fun provideFriendRemoteDataSource(splitterApiService: SplitterApiService): FriendRemoteDataSource {
        return RetrofitFriendRemoteDataSource(splitterApiService)
    }

    @Singleton
    @Provides
    fun provideExpenseRemoteDataSource(splitterApiService: SplitterApiService): ExpenseRemoteDataSource {
        return RetrofitExpenseRemoteDataSource(splitterApiService)
    }
}