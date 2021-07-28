package com.weljak.splittermobile.presentation.viewmodel.friend

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.usecase.friend.GetCurrentUserFriendListUseCase
import com.weljak.splittermobile.presentation.util.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FriendViewModel(
    private val app: Application,
    private val getCurrentUserFriendListUseCase: GetCurrentUserFriendListUseCase
) : AndroidViewModel(app) {
    private val _currentUserFriendship =
        MutableLiveData<Resource<SplitterApiResponse<Friendship>>>()
    val currentUserFriendship: LiveData<Resource<SplitterApiResponse<Friendship>>>
        get() = _currentUserFriendship

    fun getCurrentUserFriendList(token: String) = viewModelScope.launch(Dispatchers.IO) {
        _currentUserFriendship.postValue(Resource.Loading())
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = getCurrentUserFriendListUseCase.execute(token)
                _currentUserFriendship.postValue(response)
            } else {
                Toast.makeText(app, "No internet connection", Toast.LENGTH_LONG).show()
                _currentUserFriendship.postValue(Resource.Error("No internetConnection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _currentUserFriendship.postValue(Resource.Error(e.message.toString()))
        }
    }
}