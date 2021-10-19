package com.weljak.splittermobile.data.repository.datasource.group

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.group.CreateGroupForm
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import retrofit2.Response

interface GroupRemoteDataSource {
    suspend fun createGroup(
        token: String,
        createGroupForm: CreateGroupForm
    ): Response<SplitterApiResponse<Group>>

    suspend fun getCurrentUserGroups(token: String): Response<SplitterApiResponse<List<Group>>>

    suspend fun getGroupById(
        token: String,
        id: String
    ): Response<SplitterApiResponse<Group>>

    suspend fun deleteGroupById(
        token: String,
        id: String
    ): Response<SplitterApiResponse<Void>>

    suspend fun findGroupsByName(
        token: String,
        name: String
    ): Response<SplitterApiResponse<List<Group>>>

    suspend fun addUsersToGroup(
        token: String,
        id: String,
        toAdd: ManageGroupMembershipRequest
    ): Response<SplitterApiResponse<Group>>

    suspend fun removeMembersFromGroup(
        token: String,
        id: String,
        toDelete: ManageGroupMembershipRequest
    ): Response<SplitterApiResponse<Group>>


    suspend fun leaveGroup(
        token: String,
        id: String
    ): Response<SplitterApiResponse<Void>>
}