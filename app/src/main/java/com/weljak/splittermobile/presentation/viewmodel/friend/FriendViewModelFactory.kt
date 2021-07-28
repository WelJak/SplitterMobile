package com.weljak.splittermobile.presentation.viewmodel.friend

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weljak.splittermobile.domain.usecase.friend.GetCurrentUserFriendListUseCase

class FriendViewModelFactory(
    private val app: Application,
private val getCurrentUserFriendListUseCase: GetCurrentUserFriendListUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return FriendViewModel(
            app,
            getCurrentUserFriendListUseCase
        ) as T
    }
}