package com.weljak.splittermobile.presentation.di.module

import com.weljak.splittermobile.data.api.SplitterApiService
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
}