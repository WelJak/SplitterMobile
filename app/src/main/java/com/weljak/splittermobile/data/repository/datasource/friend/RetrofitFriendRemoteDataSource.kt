package com.weljak.splittermobile.data.repository.datasource.friend

import com.weljak.splittermobile.data.api.SplitterApiService
import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import retrofit2.Response

class RetrofitFriendRemoteDataSource(private val splitterApiService: SplitterApiService): FriendRemoteDataSource {
    override suspend fun getCurrentUserFriendList(token: String): Response<SplitterApiResponse<Friendship>> {
        return splitterApiService.getCurrentUserFriendList(token)
    }

    override suspend fun createFriendRequest(
        token: String,
        friendshipRequestCreationForm: FriendshipRequestCreationForm
    ): Response<SplitterApiResponse<FriendshipRequest>> {
        return splitterApiService.createFriendRequest(token, friendshipRequestCreationForm)
    }

    override suspend fun getCurrentUserSentFriendRequests(token: String): Response<SplitterApiResponse<List<FriendshipRequest>>> {
        return splitterApiService.getCurrentUserSentFriendRequests(token)
    }

    override suspend fun getCurrentUserReceivedFriendRequests(token: String): Response<SplitterApiResponse<List<FriendshipRequest>>> {
        return splitterApiService.getCurrentUserReceivedFriendRequests(token)
    }
}