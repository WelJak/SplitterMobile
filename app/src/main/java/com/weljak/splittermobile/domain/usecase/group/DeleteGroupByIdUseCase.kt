package com.weljak.splittermobile.domain.usecase.group

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.group.GroupRepository

class DeleteGroupByIdUseCase(private val groupRepository: GroupRepository) {
    suspend fun execute(token: String, id: String): Resource<SplitterApiResponse<Void>> {
        return groupRepository.deleteGroupById(token, id)
    }
}