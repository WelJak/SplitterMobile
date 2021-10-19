package com.weljak.splittermobile.domain.usecase.group

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.group.GroupRepository

class AddUsersToGroupUseCase(private val groupRepository: GroupRepository) {
    suspend fun execute(token: String, id: String, toAdd: ManageGroupMembershipRequest): Resource<SplitterApiResponse<Group>> {
        return groupRepository.addUsersToGroup(token, id, toAdd)
    }
}