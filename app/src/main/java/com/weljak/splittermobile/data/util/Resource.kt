package com.weljak.splittermobile.data.util

import com.weljak.splittermobile.data.model.SplitterApiResponse
import retrofit2.Response

sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    companion object {
        fun <T>valueOf(splitterApiResponse: Response<SplitterApiResponse<T>>):Resource<SplitterApiResponse<T>> {
            if (splitterApiResponse.isSuccessful) {
                splitterApiResponse.body()?.let { result ->
                    return Success(result)
                }
            }
            return Error(splitterApiResponse.message())
        }
    }
}