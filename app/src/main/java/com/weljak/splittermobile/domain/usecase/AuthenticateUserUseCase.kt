package com.weljak.splittermobile.domain.usecase

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.user.UserRepository

class AuthenticateUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(authenticationRequest: AuthenticationRequest): Resource<SplitterApiResponse<AuthenticationResponse>> {
        return userRepository.authenticateUser(authenticationRequest)
    }
}