package com.weljak.splittermobile.data.repository.datasource.user

import com.weljak.splittermobile.data.api.SplitterApiService
import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import retrofit2.Response

class RetrofitUserRemoteDataSource(private val splitterApiService: SplitterApiService): UserRemoteDataSource {
    override suspend fun authenticateUser(authenticationRequest: AuthenticationRequest): Response<SplitterApiResponse<AuthenticationResponse>> {
        return splitterApiService.authenticateUser(authenticationRequest)
    }

    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): Response<SplitterApiResponse<UserDetails>> {
        return splitterApiService.registerUser(registerUserRequest)
    }

    override suspend fun getUserDetails(username: String): Response<SplitterApiResponse<UserDetails>> {
        return splitterApiService.getUserDetails(username)
    }
}