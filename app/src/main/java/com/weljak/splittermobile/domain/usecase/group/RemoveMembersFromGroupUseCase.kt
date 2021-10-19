package com.weljak.splittermobile.domain.usecase.group

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.group.GroupRepository

class RemoveMembersFromGroupUseCase(private val groupRepository: GroupRepository) {
    suspend fun execute(token: String, id: String, toDelete: ManageGroupMembershipRequest): Resource<SplitterApiResponse<Group>> {
        return groupRepository.removeMembersFromGroup(token, id, toDelete)
    }
}