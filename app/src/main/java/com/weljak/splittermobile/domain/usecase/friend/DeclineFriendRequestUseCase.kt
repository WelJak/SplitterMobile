package com.weljak.splittermobile.domain.usecase.friend

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.friend.FriendRepository

class DeclineFriendRequestUseCase(private val friendRepository: FriendRepository) {
    suspend fun execute(token: String, reqId: String): Resource<SplitterApiResponse<Void>> {
        return friendRepository.declineFriendRequest(token, reqId)
    }
}