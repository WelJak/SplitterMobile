package com.weljak.splittermobile.data.repository.datasource.friend

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import retrofit2.Response

interface FriendRemoteDataSource {
    suspend fun getCurrentUserFriendList(token: String): Response<SplitterApiResponse<Friendship>>
}