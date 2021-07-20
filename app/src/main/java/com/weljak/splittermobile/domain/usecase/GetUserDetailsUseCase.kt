package com.weljak.splittermobile.domain.usecase

import com.weljak.splittermobile.data.model.SplitterApiResponse
import com.weljak.splittermobile.data.model.user.UserDetails
import com.weljak.splittermobile.data.util.Resource
import com.weljak.splittermobile.domain.repository.user.UserRepository

class GetUserDetailsUseCase(private val userRepository: UserRepository) {
    suspend fun execute(username: String, token: String): Resource<SplitterApiResponse<UserDetails>> {
        return userRepository.getUserDetails(username, token)
    }
}