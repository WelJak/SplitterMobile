package com.weljak.splittermobile.data.model.friend.request

import com.weljak.splittermobile.data.model.friend.Friend

data class FriendshipRequest(
    val id: String,
    val confirmationId:String,
    val username: String,
    val potentialFriend: Friend
)
