package com.weljak.splittermobile.data.repository.datasource.user

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserRemoteDataSource {
    suspend fun authenticateUser(authenticationRequest: AuthenticationRequest): Response<SplitterApiResponse<AuthenticationResponse>>
    suspend fun registerUser(registerUserRequest: RegisterUserRequest): Response<SplitterApiResponse<UserDetails>>
    suspend fun getUserDetails(username: String): Response<SplitterApiResponse<UserDetails>>
}