package com.weljak.splittermobile.data.repository.datasource.friend

import com.weljak.splittermobile.data.model.api.ConfirmationResponse
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import retrofit2.Response
import retrofit2.http.DELETE
import retrofit2.http.Header
import retrofit2.http.Path

interface FriendRemoteDataSource {
    suspend fun getCurrentUserFriendList(token: String): Response<SplitterApiResponse<Friendship>>
    suspend fun createFriendRequest(token: String, friendshipRequestCreationForm: FriendshipRequestCreationForm): Response<SplitterApiResponse<FriendshipRequest>>
    suspend fun getCurrentUserSentFriendRequests(token:String): Response<SplitterApiResponse<List<FriendshipRequest>>>
    suspend fun getCurrentUserReceivedFriendRequests(token:String): Response<SplitterApiResponse<List<FriendshipRequest>>>
    suspend fun confirmFriendRequest(token: String, reqId: String, confirmationId: String): Response<SplitterApiResponse<ConfirmationResponse>>
    suspend fun declineFriendRequest(token: String, reqId: String): Response<SplitterApiResponse<Void>>
}