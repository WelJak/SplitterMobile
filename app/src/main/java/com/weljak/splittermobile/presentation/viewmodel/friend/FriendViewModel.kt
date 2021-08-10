package com.weljak.splittermobile.presentation.viewmodel.friend

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.weljak.splittermobile.data.model.api.ConfirmationResponse
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.usecase.friend.*
import com.weljak.splittermobile.presentation.util.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class FriendViewModel(
    private val app: Application,
    private val getCurrentUserFriendListUseCase: GetCurrentUserFriendListUseCase,
    private val createFriendRequestUseCase: CreateFriendRequestUseCase,
    private val getCurrentUserSentFriendRequestsUseCase: GetCurrentUserSentFriendRequestsUseCase,
    private val getCurrentUserReceivedFriendRequestsUseCase: GetCurrentUserReceivedFriendRequestsUseCase,
    private val confirmFriendRequestUseCase: ConfirmFriendRequestUseCase,
    private val declineFriendRequestUseCase: DeclineFriendRequestUseCase
) : AndroidViewModel(app) {
    private val _currentUserFriendship =
        MutableLiveData<Resource<SplitterApiResponse<Friendship>>>()
    val currentUserFriendship: LiveData<Resource<SplitterApiResponse<Friendship>>>
        get() = _currentUserFriendship

    private val _addFriendReqResponse = MutableLiveData<Resource<SplitterApiResponse<FriendshipRequest>>>()
    val addFriendReqResponse: LiveData<Resource<SplitterApiResponse<FriendshipRequest>>>
        get() = _addFriendReqResponse

    private val _sentFriendRequests = MutableLiveData<Resource<SplitterApiResponse<List<FriendshipRequest>>>>()
    val sentFriendRequests: LiveData<Resource<SplitterApiResponse<List<FriendshipRequest>>>>
        get() = _sentFriendRequests

    private val _receivedFriendRequests = MutableLiveData<Resource<SplitterApiResponse<List<FriendshipRequest>>>>()
    val receivedFriendRequests: LiveData<Resource<SplitterApiResponse<List<FriendshipRequest>>>>
        get() = _receivedFriendRequests

    private val _confirmFriendReqResponse = MutableLiveData<Resource<SplitterApiResponse<ConfirmationResponse>>>()
    val confirmFriendReqResponse: LiveData<Resource<SplitterApiResponse<ConfirmationResponse>>>
        get() = _confirmFriendReqResponse

    private val _declineFriendReqResponse = MutableLiveData<Resource<SplitterApiResponse<Void>>>()
    val declineFriendReqResponse: LiveData<Resource<SplitterApiResponse<Void>>>
        get() = _declineFriendReqResponse

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

    fun createFriendRequest(token: String, friendshipRequestCreationForm: FriendshipRequestCreationForm) = viewModelScope.launch(Dispatchers.IO) {
        _addFriendReqResponse.postValue(Resource.Loading())
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = createFriendRequestUseCase.execute(token, friendshipRequestCreationForm)
                _addFriendReqResponse.postValue(response)
            } else {
                Toast.makeText(app, "No internet connection", Toast.LENGTH_LONG).show()
                _addFriendReqResponse.postValue(Resource.Error("No internetConnection"))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _addFriendReqResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getCurrentUserSentFriendRequests(token: String) = viewModelScope.launch(Dispatchers.IO) {
        _sentFriendRequests.postValue(Resource.Loading())
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = getCurrentUserSentFriendRequestsUseCase.execute(token)
                _sentFriendRequests.postValue(response)
            } else {
                Toast.makeText(app, "No internet connection", Toast.LENGTH_LONG).show()
                _sentFriendRequests.postValue(Resource.Error("No internetConnection"))
            }
        } catch (e:Exception) {
            e.printStackTrace()
            _sentFriendRequests.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun getCurrentUserReceivedFriendRequests(token: String) = viewModelScope.launch(Dispatchers.IO) {
        _receivedFriendRequests.postValue(Resource.Loading())
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = getCurrentUserReceivedFriendRequestsUseCase.execute(token)
                _receivedFriendRequests.postValue(response)
            } else {
                Toast.makeText(app, "No internet connection", Toast.LENGTH_LONG).show()
                _receivedFriendRequests.postValue(Resource.Error("No internetConnection"))
            }
        } catch (e:Exception) {
            e.printStackTrace()
            _receivedFriendRequests.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun acceptFriendRequest(token: String, friendshipRequest: FriendshipRequest) = viewModelScope.launch(Dispatchers.IO) {
        _confirmFriendReqResponse.postValue(Resource.Loading())
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = confirmFriendRequestUseCase.execute(token, friendshipRequest.id, friendshipRequest.confirmationId)
                _confirmFriendReqResponse.postValue(response)
            } else {
                Toast.makeText(app, "No internet connection", Toast.LENGTH_LONG).show()
                _confirmFriendReqResponse.postValue(Resource.Error("No internetConnection"))
            }
        } catch (e:Exception) {
            e.printStackTrace()
            _confirmFriendReqResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    fun declineFriendRequest(token: String, friendshipRequest: FriendshipRequest) = viewModelScope.launch(Dispatchers.IO) {
        _declineFriendReqResponse.postValue(Resource.Loading())
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = declineFriendRequestUseCase.execute(token, friendshipRequest.id)
                _declineFriendReqResponse.postValue(response)
            } else {
                Toast.makeText(app, "No internet connection", Toast.LENGTH_LONG).show()
                _declineFriendReqResponse.postValue(Resource.Error("No internetConnection"))
            }
        } catch (e:Exception) {
            e.printStackTrace()
            _declineFriendReqResponse.postValue(Resource.Error(e.message.toString()))
        }
    }
}