package com.weljak.splittermobile.data.model.group

data class CreateGroupForm(
    val groupName: String,
    val members: HashSet<String> ?= HashSet()
)
