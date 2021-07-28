package com.weljak.splittermobile.domain.repository.friend

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.util.Resource

interface FriendRepository {
    suspend fun getCurrentUserFriendList(token: String): Resource<SplitterApiResponse<Friendship>>
}