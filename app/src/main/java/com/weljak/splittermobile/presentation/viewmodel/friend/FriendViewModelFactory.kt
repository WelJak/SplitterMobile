package com.weljak.splittermobile.presentation.viewmodel.friend

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weljak.splittermobile.domain.usecase.friend.*

class FriendViewModelFactory(
    private val app: Application,
    private val getCurrentUserFriendListUseCase: GetCurrentUserFriendListUseCase,
    private val createFriendRequestUseCase: CreateFriendRequestUseCase,
    private val getCurrentUserSentFriendRequestsUseCase: GetCurrentUserSentFriendRequestsUseCase,
    private val getCurrentUserReceivedFriendRequestsUseCase: GetCurrentUserReceivedFriendRequestsUseCase,
    private val confirmFriendRequestUseCase: ConfirmFriendRequestUseCase,
    private val declineFriendRequestUseCase: DeclineFriendRequestUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FriendViewModel(
            app,
            getCurrentUserFriendListUseCase,
            createFriendRequestUseCase,
            getCurrentUserSentFriendRequestsUseCase,
            getCurrentUserReceivedFriendRequestsUseCase,
            confirmFriendRequestUseCase,
            declineFriendRequestUseCase
        ) as T
    }
}