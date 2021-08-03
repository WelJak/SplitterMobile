package com.weljak.splittermobile.presentation.viewmodel.friend

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weljak.splittermobile.domain.usecase.friend.CreateFriendRequestUseCase
import com.weljak.splittermobile.domain.usecase.friend.GetCurrentUserFriendListUseCase
import com.weljak.splittermobile.domain.usecase.friend.GetCurrentUserReceivedFriendRequestsUseCase
import com.weljak.splittermobile.domain.usecase.friend.GetCurrentUserSentFriendRequestsUseCase

class FriendViewModelFactory(
    private val app: Application,
    private val getCurrentUserFriendListUseCase: GetCurrentUserFriendListUseCase,
    private val createFriendRequestUseCase: CreateFriendRequestUseCase,
    private val getCurrentUserSentFriendRequestsUseCase: GetCurrentUserSentFriendRequestsUseCase,
    private val getCurrentUserReceivedFriendRequestsUseCase: GetCurrentUserReceivedFriendRequestsUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FriendViewModel(
            app,
            getCurrentUserFriendListUseCase,
            createFriendRequestUseCase,
            getCurrentUserSentFriendRequestsUseCase,
            getCurrentUserReceivedFriendRequestsUseCase
        ) as T
    }
}