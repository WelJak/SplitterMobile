package com.weljak.splittermobile.domain.repository.friend

import com.weljak.splittermobile.data.model.api.ConfirmationResponse
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import com.weljak.splittermobile.data.util.Resource

interface FriendRepository {
    suspend fun getCurrentUserFriendList(token: String): Resource<SplitterApiResponse<Friendship>>
    suspend fun createFriendRequest(token: String, friendshipRequestCreationForm: FriendshipRequestCreationForm): Resource<SplitterApiResponse<FriendshipRequest>>
    suspend fun getCurrentUserSentFriendRequests(token:String): Resource<SplitterApiResponse<List<FriendshipRequest>>>
    suspend fun getCurrentUserReceivedFriendRequests(token:String): Resource<SplitterApiResponse<List<FriendshipRequest>>>
    suspend fun confirmFriendRequest(token: String, reqId: String, confirmationId: String): Resource<SplitterApiResponse<ConfirmationResponse>>
    suspend fun declineFriendRequest(token: String, reqId: String): Resource<SplitterApiResponse<Void>>
}