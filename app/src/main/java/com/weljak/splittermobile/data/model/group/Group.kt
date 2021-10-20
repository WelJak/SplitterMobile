package com.weljak.splittermobile.data.model.group

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class Group(
    val id: String,
    val groupName: String,
    val createdBy: String,
    val createdAt: LocalDateTime,
    val members: List<String> ?= emptyList()
) : Parcelable
