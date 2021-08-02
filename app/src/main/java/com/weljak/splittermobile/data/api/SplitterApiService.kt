package com.weljak.splittermobile.data.api

import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import retrofit2.Response
import retrofit2.http.*

interface SplitterApiService {
    @POST("user/auth/login")
    suspend fun authenticateUser(@Body authenticationRequest: AuthenticationRequest):Response<SplitterApiResponse<AuthenticationResponse>>

    @POST("user/create")
    suspend fun registerUser(@Body registerUserRequest: RegisterUserRequest): Response<SplitterApiResponse<UserDetails>>

    @GET("user/{username}")
    suspend fun getUserDetails(@Path("username") username: String, @Header("Authorization")token:String): Response<SplitterApiResponse<UserDetails>>

    @GET("friends")
    suspend fun getCurrentUserFriendList(@Header("Authorization")token:String): Response<SplitterApiResponse<Friendship>>

    @POST("friends/request/create")
    suspend fun createFriendRequest(@Header("Authorization")token:String, @Body friendshipRequestCreationForm: FriendshipRequestCreationForm): Response<SplitterApiResponse<FriendshipRequest>>
}