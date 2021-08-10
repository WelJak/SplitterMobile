package com.weljak.splittermobile.data.model.api

data class SplitterApiResponse<T>(
    val message: String,
    val path: String,
    val payload: T? = null,
    val status: String,
    val statusCode: Int,
    val success: Boolean,
    val timestamp: String
)