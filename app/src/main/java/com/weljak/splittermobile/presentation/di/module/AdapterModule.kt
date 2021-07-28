package com.weljak.splittermobile.presentation.di.module

import com.weljak.splittermobile.presentation.adapter.FriendsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AdapterModule {
    @Provides
    @Singleton
    fun provideFriendsAdapter():FriendsAdapter {
        return FriendsAdapter()
    }
}