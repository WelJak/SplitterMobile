package com.weljak.splittermobile.domain.usecase.group

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.group.GroupRepository

class GetCurrentUserGroupsUseCase(private val groupRepository: GroupRepository) {
    suspend fun execute(token: String): Resource<SplitterApiResponse<List<Group>>> {
        return groupRepository.getCurrentUserGroups(token)
    }
}