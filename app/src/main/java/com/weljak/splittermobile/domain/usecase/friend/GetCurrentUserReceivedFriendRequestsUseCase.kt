package com.weljak.splittermobile.domain.usecase.friend

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.friend.FriendRepository

class GetCurrentUserReceivedFriendRequestsUseCase(private val friendRepository: FriendRepository) {
    suspend fun execute(token: String): Resource<SplitterApiResponse<List<FriendshipRequest>>> {
        return friendRepository.getCurrentUserReceivedFriendRequests(token)
    }
}