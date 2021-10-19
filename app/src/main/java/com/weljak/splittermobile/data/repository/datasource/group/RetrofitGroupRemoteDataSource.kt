package com.weljak.splittermobile.data.repository.datasource.group

import com.weljak.splittermobile.data.api.SplitterApiService
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.group.CreateGroupForm
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import retrofit2.Response

class RetrofitGroupRemoteDataSource(private val splitterApiService: SplitterApiService): GroupRemoteDataSource {
    override suspend fun createGroup(
        token: String,
        createGroupForm: CreateGroupForm
    ): Response<SplitterApiResponse<Group>> {
        return splitterApiService.createGroup(token, createGroupForm)
    }

    override suspend fun getCurrentUserGroups(token: String): Response<SplitterApiResponse<List<Group>>> {
        return splitterApiService.getCurrentUserGroups(token)
    }

    override suspend fun getGroupById(
        token: String,
        id: String
    ): Response<SplitterApiResponse<Group>> {
        return splitterApiService.getGroupById(token, id)
    }

    override suspend fun deleteGroupById(
        token: String,
        id: String
    ): Response<SplitterApiResponse<Void>> {
        return splitterApiService.deleteGroupById(token, id)
    }

    override suspend fun findGroupsByName(
        token: String,
        name: String
    ): Response<SplitterApiResponse<List<Group>>> {
        return splitterApiService.findGroupsByName(token, name)
    }

    override suspend fun addUsersToGroup(
        token: String,
        id: String,
        toAdd: ManageGroupMembershipRequest
    ): Response<SplitterApiResponse<Group>> {
        return splitterApiService.addUsersToGroup(token, id, toAdd)
    }

    override suspend fun removeMembersFromGroup(
        token: String,
        id: String,
        toDelete: ManageGroupMembershipRequest
    ): Response<SplitterApiResponse<Group>> {
        return splitterApiService.removeMembersFromGroup(token, id, toDelete)
    }

    override suspend fun leaveGroup(
        token: String,
        id: String
    ): Response<SplitterApiResponse<Void>> {
        return splitterApiService.leaveGroup(token, id)
    }
}