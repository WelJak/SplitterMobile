package com.weljak.splittermobile.domain.usecase.friend

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.friend.FriendRepository

class CreateFriendRequestUseCase(private val friendRepository: FriendRepository) {
    suspend fun execute(token: String, friendshipRequestCreationForm: FriendshipRequestCreationForm): Resource<SplitterApiResponse<FriendshipRequest>> {
        return friendRepository.createFriendRequest(token, friendshipRequestCreationForm)
    }
}