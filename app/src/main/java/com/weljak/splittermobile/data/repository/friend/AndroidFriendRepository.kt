package com.weljak.splittermobile.data.repository.friend

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.repository.datasource.friend.FriendRemoteDataSource
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.friend.FriendRepository

class AndroidFriendRepository(private val friendRemoteDataSource: FriendRemoteDataSource): FriendRepository {
    override suspend fun getCurrentUserFriendList(token: String): Resource<SplitterApiResponse<Friendship>> {
        return Resource.valueOf(friendRemoteDataSource.getCurrentUserFriendList(token))
    }
}