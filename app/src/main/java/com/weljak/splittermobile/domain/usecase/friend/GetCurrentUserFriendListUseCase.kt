package com.weljak.splittermobile.domain.usecase.friend

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.friend.FriendRepository

class GetCurrentUserFriendListUseCase(private val friendRepository: FriendRepository) {
    suspend fun execute(token: String): Resource<SplitterApiResponse<Friendship>> {
        return friendRepository.getCurrentUserFriendList(token)
    }
}