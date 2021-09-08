package com.weljak.splittermobile.presentation.di.module

import android.app.Application
import android.content.Context
import com.weljak.splittermobile.presentation.adapter.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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

    @Provides
    @Singleton
    fun provideSentFriendRequestsAdapter(): SentFriendRequestsAdapter {
        return SentFriendRequestsAdapter()
    }

    @Provides
    @Singleton
    fun provideReceivedFriendRequestsAdapter(): ReceivedFriendRequestsAdapter {
        return ReceivedFriendRequestsAdapter()
    }

    @Provides
    @Singleton
    fun provideUnsettledExpensesAdapter(): UnsettledExpensesAdapter {
        return UnsettledExpensesAdapter()
    }

    @Provides
    @Singleton
    fun provideDebtorsAdapter(): DebtorsAdapter {
        return DebtorsAdapter()
    }
}