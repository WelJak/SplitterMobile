package com.weljak.splittermobile.data.repository.friend

import com.weljak.splittermobile.data.model.api.ConfirmationResponse
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import com.weljak.splittermobile.data.repository.datasource.friend.FriendRemoteDataSource
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.friend.FriendRepository

class AndroidFriendRepository(private val friendRemoteDataSource: FriendRemoteDataSource): FriendRepository {
    override suspend fun getCurrentUserFriendList(token: String): Resource<SplitterApiResponse<Friendship>> {
        return Resource.valueOf(friendRemoteDataSource.getCurrentUserFriendList(token))
    }

    override suspend fun createFriendRequest(
        token: String,
        friendshipRequestCreationForm: FriendshipRequestCreationForm
    ): Resource<SplitterApiResponse<FriendshipRequest>> {
        return Resource.valueOf(friendRemoteDataSource.createFriendRequest(token, friendshipRequestCreationForm))
    }

    override suspend fun getCurrentUserSentFriendRequests(token: String): Resource<SplitterApiResponse<List<FriendshipRequest>>> {
        return Resource.valueOf(friendRemoteDataSource.getCurrentUserSentFriendRequests(token))
    }

    override suspend fun getCurrentUserReceivedFriendRequests(token: String): Resource<SplitterApiResponse<List<FriendshipRequest>>> {
        return Resource.valueOf(friendRemoteDataSource.getCurrentUserReceivedFriendRequests(token))
    }

    override suspend fun confirmFriendRequest(
        token: String,
        reqId: String,
        confirmationId: String
    ): Resource<SplitterApiResponse<ConfirmationResponse>> {
        return Resource.valueOf(friendRemoteDataSource.confirmFriendRequest(token, reqId, confirmationId))
    }

    override suspend fun declineFriendRequest(
        token: String,
        reqId: String
    ): Resource<SplitterApiResponse<Void>> {
        return Resource.valueOf(friendRemoteDataSource.declineFriendRequest(token, reqId))
    }
}