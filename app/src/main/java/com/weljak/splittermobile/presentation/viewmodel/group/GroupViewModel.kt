package com.weljak.splittermobile.presentation.viewmodel.group

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.group.CreateGroupForm
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.usecase.group.*
import com.weljak.splittermobile.presentation.util.ConnectionUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GroupViewModel(
    private val app: Application,
    private val createGroupUseCase: CreateGroupUseCase,
    private val getCurrentUserGroupsUseCase: GetCurrentUserGroupsUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val deleteGroupByIdUseCase: DeleteGroupByIdUseCase,
    private val findGroupsByNameUseCase: FindGroupsByNameUseCase,
    private val addUsersToGroupUseCase: AddUsersToGroupUseCase,
    private val removeMembersFromGroupUseCase: RemoveMembersFromGroupUseCase,
    private val leaveGroupUseCase: LeaveGroupUseCase
): AndroidViewModel(app) {
    private val _createGroupResponse = MutableLiveData<Resource<SplitterApiResponse<Group>>> ()
    val createGroupResponse: LiveData<Resource<SplitterApiResponse<Group>>>
        get() = _createGroupResponse

    fun createGroup(token: String, createGroupForm: CreateGroupForm) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = createGroupUseCase.execute(token, createGroupForm)
                _createGroupResponse.postValue(response)
            } else {
                _createGroupResponse.postValue(Resource.noInternetConnection())
            }
        } catch (e: Exception) {
            _createGroupResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _getCurrentUserGroupsResponse = MutableLiveData<Resource<SplitterApiResponse<List<Group>>>> ()
    val getCurrentUserGroupsResponse: LiveData<Resource<SplitterApiResponse<List<Group>>>>
        get() = _getCurrentUserGroupsResponse

    fun getCurrentUserGroups(token: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = getCurrentUserGroupsUseCase.execute(token)
                _getCurrentUserGroupsResponse.postValue(response)
            } else {
                _getCurrentUserGroupsResponse.postValue(Resource.noInternetConnection())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _getCurrentUserGroupsResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _getGroupByIdResponse = MutableLiveData<Resource<SplitterApiResponse<Group>>> ()
    val getGroupByIdResponse: LiveData<Resource<SplitterApiResponse<Group>>>
        get() = _getGroupByIdResponse

    fun getGroupById(token: String, id: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = getGroupByIdUseCase.execute(token, id)
                _getGroupByIdResponse.postValue(response)
            } else {
                _getGroupByIdResponse.postValue(Resource.noInternetConnection())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _getGroupByIdResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _deleteGroupByIdResponse = MutableLiveData<Resource<SplitterApiResponse<Void>>> ()
    val deleteGroupByIdResponse : LiveData<Resource<SplitterApiResponse<Void>>>
        get() = _deleteGroupByIdResponse

    fun deleteGroupById(token: String, id: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response =  deleteGroupByIdUseCase.execute(token, id)
                _deleteGroupByIdResponse.postValue(response)
            } else {
                _deleteGroupByIdResponse.postValue(Resource.noInternetConnection())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _deleteGroupByIdResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _findGroupsByNameResponse = MutableLiveData<Resource<SplitterApiResponse<List<Group>>>> ()
    val findGroupsByNameResponse: LiveData<Resource<SplitterApiResponse<List<Group>>>>
        get() = _findGroupsByNameResponse

    fun findGroupsByName(token: String, name: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = findGroupsByNameUseCase.execute(token, name)
                _findGroupsByNameResponse.postValue(response)
            } else {
                _findGroupsByNameResponse.postValue(Resource.noInternetConnection())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _findGroupsByNameResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _addUsersToGroupResponse = MutableLiveData<Resource<SplitterApiResponse<Group>>> ()
    val addUsersToGroupResponse: LiveData<Resource<SplitterApiResponse<Group>>>
        get() = _addUsersToGroupResponse

    fun addUsersToGroup(token: String, id: String, toAdd: ManageGroupMembershipRequest) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = addUsersToGroupUseCase.execute(token, id, toAdd)
                _addUsersToGroupResponse.postValue(response)
            } else {
                _addUsersToGroupResponse.postValue(Resource.noInternetConnection())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _addUsersToGroupResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _removeUsersFromGroupResponse = MutableLiveData<Resource<SplitterApiResponse<Group>>> ()
    val removeUsersFromGroupResponse: LiveData<Resource<SplitterApiResponse<Group>>>
        get() = _removeUsersFromGroupResponse

    fun removeUsersFromGroup(token: String, id: String, toDelete: ManageGroupMembershipRequest) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = removeMembersFromGroupUseCase.execute(token, id, toDelete)
                _removeUsersFromGroupResponse.postValue(response)
            } else {
                _removeUsersFromGroupResponse.postValue(Resource.noInternetConnection())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _removeUsersFromGroupResponse.postValue(Resource.Error(e.message.toString()))
        }
    }

    private val _leaveGroupResponse = MutableLiveData<Resource<SplitterApiResponse<Void>>> ()
    val leaveGroupResponse: LiveData<Resource<SplitterApiResponse<Void>>>
        get() = _leaveGroupResponse

    fun leaveGroup(token: String, id: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            if (ConnectionUtils.isNetworkAvailable(app)) {
                val response = leaveGroupUseCase.execute(token, id)
                _leaveGroupResponse.postValue(response)
            } else {
                _leaveGroupResponse.postValue(Resource.noInternetConnection())
            }
        } catch (e: Exception) {
            e.printStackTrace()
            _leaveGroupResponse.postValue(Resource.Error(e.message.toString()))
        }
    }
}
