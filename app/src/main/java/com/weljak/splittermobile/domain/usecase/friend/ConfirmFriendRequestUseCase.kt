package com.weljak.splittermobile.domain.usecase.friend

import com.weljak.splittermobile.data.model.api.ConfirmationResponse
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.friend.FriendRepository

class ConfirmFriendRequestUseCase(private val friendRepository: FriendRepository) {
    suspend fun execute(
        token: String,
        reqId: String,
        confirmationId: String
    ): Resource<SplitterApiResponse<ConfirmationResponse>> {
        return friendRepository.confirmFriendRequest(token, reqId, confirmationId)
    }
}