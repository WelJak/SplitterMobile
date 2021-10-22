package com.weljak.splittermobile.data.util

import com.google.gson.JsonParser
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import retrofit2.Response

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    companion object {
        private const val NO_INTERNET_CONNECTION_MESSAGE = "No internet connection"

        fun <T> valueOf(splitterApiResponse: Response<SplitterApiResponse<T>>):Resource<SplitterApiResponse<T>> {
            if (splitterApiResponse.isSuccessful) {
                splitterApiResponse.body()?.let { result ->
                    return Success(result)
                }
            }
            val faultResponse = JsonParser.parseReader(splitterApiResponse.errorBody()?.charStream()).asJsonObject
            return Error(faultResponse.get("message").asString)
        }

        fun <T> noInternetConnection(): Resource<SplitterApiResponse<T>> {
            return Error(NO_INTERNET_CONNECTION_MESSAGE)
        }
    }
}