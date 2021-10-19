package com.weljak.splittermobile.data.repository.group

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.group.CreateGroupForm
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import com.weljak.splittermobile.data.repository.datasource.group.GroupRemoteDataSource
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.group.GroupRepository

class AndroidGroupRepository(private val groupRemoteDataSource: GroupRemoteDataSource): GroupRepository {
    override suspend fun createGroup(
        token: String,
        createGroupForm: CreateGroupForm
    ): Resource<SplitterApiResponse<Group>> {
        return Resource.valueOf(groupRemoteDataSource.createGroup(token, createGroupForm))
    }

    override suspend fun getCurrentUserGroups(token: String): Resource<SplitterApiResponse<List<Group>>> {
        return Resource.valueOf(groupRemoteDataSource.getCurrentUserGroups(token))
    }

    override suspend fun getGroupById(
        token: String,
        id: String
    ): Resource<SplitterApiResponse<Group>> {
        return Resource.valueOf(groupRemoteDataSource.getGroupById(token, id))
    }

    override suspend fun deleteGroupById(
        token: String,
        id: String
    ): Resource<SplitterApiResponse<Void>> {
        return Resource.valueOf(groupRemoteDataSource.deleteGroupById(token, id))
    }

    override suspend fun findGroupsByName(
        token: String,
        name: String
    ): Resource<SplitterApiResponse<List<Group>>> {
        return Resource.valueOf(groupRemoteDataSource.findGroupsByName(token, name))
    }

    override suspend fun addUsersToGroup(
        token: String,
        id: String,
        toAdd: ManageGroupMembershipRequest
    ): Resource<SplitterApiResponse<Group>> {
        return Resource.valueOf(groupRemoteDataSource.addUsersToGroup(token, id, toAdd))
    }

    override suspend fun removeMembersFromGroup(
        token: String,
        id: String,
        toDelete: ManageGroupMembershipRequest
    ): Resource<SplitterApiResponse<Group>> {
        return Resource.valueOf(groupRemoteDataSource.removeMembersFromGroup(token, id, toDelete))
    }

    override suspend fun leaveGroup(
        token: String,
        id: String
    ): Resource<SplitterApiResponse<Void>> {
        return Resource.valueOf(groupRemoteDataSource.leaveGroup(token, id))
    }
}