package com.android.trendingcermati.extention

import retrofit2.Response

internal const val UNKNOWN_CODE = -1

sealed class ApiResponse<T> {

    companion object {

        fun <T> create(response: Response<T>): ApiResponse<T> {
            val body = response.body()
            return when {
                response.isSuccessful -> {
                    if (body == null || response.code() == 403 || response.code() == 404) {
                        ApiEmptyResponse()
                    } else {
                        ApiSuccessResponse(body)
                    }
                }
                else -> {
                    ApiErrorResponse(
                        response.code(),
                        RuntimeException(response.message())
                    )
                }
            }
        }

        fun <T> create(errorCode: Int, error: Throwable): ApiErrorResponse<T> {
            return ApiErrorResponse(errorCode, error)
        }
    }
}

class ApiEmptyResponse<T> : ApiResponse<T>()

data class ApiErrorResponse<T>(val errorCode: Int, val error: Throwable) : ApiResponse<T>()

data class ApiSuccessResponse<T>(val body: T) : ApiResponse<T>()