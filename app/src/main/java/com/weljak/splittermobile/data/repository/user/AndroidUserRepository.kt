package com.weljak.splittermobile.data.repository.user

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import com.weljak.splittermobile.data.repository.datasource.user.UserRemoteDataSource
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.user.UserRepository
import retrofit2.Response

class AndroidUserRepository(private val userRemoteDataSource: UserRemoteDataSource): UserRepository {
    override suspend fun authenticateUser(authenticationRequest: AuthenticationRequest): Resource<SplitterApiResponse<AuthenticationResponse>> {
        return responseToResource(userRemoteDataSource.authenticateUser(authenticationRequest))
    }

    override suspend fun registerUser(registerUserRequest: RegisterUserRequest): Resource<SplitterApiResponse<UserDetails>> {
        return responseToResource(userRemoteDataSource.registerUser(registerUserRequest))
    }

    override suspend fun getUserDetails(username: String, token: String): Resource<SplitterApiResponse<UserDetails>> {
        return responseToResource(userRemoteDataSource.getUserDetails(username, token))
    }

    private fun <T>responseToResource(splitterApiResponse: Response<SplitterApiResponse<T>>):Resource<SplitterApiResponse<T>> {
        if (splitterApiResponse.isSuccessful) {
            splitterApiResponse.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(splitterApiResponse.message())
    }
}