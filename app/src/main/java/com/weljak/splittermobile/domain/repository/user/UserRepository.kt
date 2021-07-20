package com.weljak.splittermobile.domain.repository.user

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import com.weljak.splittermobile.data.util.Resource

interface UserRepository {
    suspend fun authenticateUser(authenticationRequest: AuthenticationRequest): Resource<SplitterApiResponse<AuthenticationResponse>>
    suspend fun registerUser(registerUserRequest: RegisterUserRequest): Resource<SplitterApiResponse<UserDetails>>
    suspend fun getUserDetails(username: String, token: String): Resource<SplitterApiResponse<UserDetails>>
}