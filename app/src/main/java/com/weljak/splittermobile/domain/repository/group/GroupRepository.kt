package com.weljak.splittermobile.domain.repository.group

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.group.CreateGroupForm
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import com.weljak.splittermobile.data.util.Resource

interface GroupRepository {
    suspend fun createGroup(
        token: String,
        createGroupForm: CreateGroupForm
    ): Resource<SplitterApiResponse<Group>>

    suspend fun getCurrentUserGroups(token: String): Resource<SplitterApiResponse<List<Group>>>

    suspend fun getGroupById(
        token: String,
        id: String
    ): Resource<SplitterApiResponse<Group>>

    suspend fun deleteGroupById(
        token: String,
        id: String
    ): Resource<SplitterApiResponse<Void>>

    suspend fun findGroupsByName(
        token: String,
        name: String
    ): Resource<SplitterApiResponse<List<Group>>>

    suspend fun addUsersToGroup(
        token: String,
        id: String,
        toAdd: ManageGroupMembershipRequest
    ): Resource<SplitterApiResponse<Group>>

    suspend fun removeMembersFromGroup(
        token: String,
        id: String,
        toDelete: ManageGroupMembershipRequest
    ): Resource<SplitterApiResponse<Group>>


    suspend fun leaveGroup(
        token: String,
        id: String
    ): Resource<SplitterApiResponse<Void>>
}
