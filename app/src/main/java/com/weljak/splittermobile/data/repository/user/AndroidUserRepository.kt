package com.weljak.splittermobile.data.repository.user

import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import com.weljak.splittermobile.data.repository.datasource.user.UserRemoteDataSource
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.user.UserRepository

class AndroidUserRepository(private val userRemoteDataSource: UserRemoteDataSource): UserRepository {
    override suspend fun authenticateUser(authenticationRequest: AuthenticationRequest): Resource<SplitterApiResponse<AuthenticationResponse>> {
        return Resource.valueOf(userRemoteDataSource.authenticateUser(authenticationRequest))
    }

    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): Resource<SplitterApiResponse<UserDetails>> {
        return Resource.valueOf(userRemoteDataSource.registerUser(registerUserRequest))
    }

    override suspend fun getUserDetails(username: String, token: String): Resource<SplitterApiResponse<UserDetails>> {
        return Resource.valueOf(userRemoteDataSource.getUserDetails(username, token))
    }
}