package com.weljak.splittermobile.data.repository.datasource.friend

import com.weljak.splittermobile.data.api.SplitterApiService
import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import retrofit2.Response

class RetrofitFriendRemoteDataSource(private val splitterApiService: SplitterApiService): FriendRemoteDataSource {
    override suspend fun getCurrentUserFriendList(token: String): Response<SplitterApiResponse<Friendship>> {
        return splitterApiService.getCurrentUserFriendList(token)
    }
}