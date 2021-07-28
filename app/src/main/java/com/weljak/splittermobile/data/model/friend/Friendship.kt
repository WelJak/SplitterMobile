package com.weljak.splittermobile.data.model.friend

data class Friendship(
    val id: String,
    val username: String,
    val friendList: List<Friend> = emptyList()
)
