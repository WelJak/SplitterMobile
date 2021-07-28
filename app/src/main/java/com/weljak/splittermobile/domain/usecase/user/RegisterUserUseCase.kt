package com.weljak.splittermobile.domain.usecase.user

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.user.UserRepository

class RegisterUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(registerUserRequest: RegisterUserRequest): Resource<SplitterApiResponse<UserDetails>> {
        return userRepository.registerUser(registerUserRequest)
    }
}