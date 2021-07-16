package com.weljak.splittermobile.data.api

import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SplitterApiService {
    @POST("user/auth/login")
    suspend fun authenticateUser(@Body authenticationRequest: AuthenticationRequest):Response<SplitterApiResponse<AuthenticationResponse>>

    @POST("user/create")
    suspend fun registerUser(@Body registerUserRequest: RegisterUserRequest): Response<SplitterApiResponse<UserDetails>>

    @GET("user/{username}")
    suspend fun getUserDetails(@Path("username") username: String): Response<SplitterApiResponse<UserDetails>>
}