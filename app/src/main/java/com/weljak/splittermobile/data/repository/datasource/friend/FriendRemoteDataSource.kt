package com.weljak.splittermobile.data.repository.datasource.friend

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import retrofit2.Response

interface FriendRemoteDataSource {
    suspend fun getCurrentUserFriendList(token: String): Response<SplitterApiResponse<Friendship>>
    suspend fun createFriendRequest(token: String, friendshipRequestCreationForm: FriendshipRequestCreationForm): Response<SplitterApiResponse<FriendshipRequest>>
    suspend fun getCurrentUserSentFriendRequests(token:String): Response<SplitterApiResponse<List<FriendshipRequest>>>
    suspend fun getCurrentUserReceivedFriendRequests(token:String): Response<SplitterApiResponse<List<FriendshipRequest>>>
}