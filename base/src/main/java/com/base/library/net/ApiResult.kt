package com.base.library.net

import com.base.library.utils.logger

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()

    //这是一种特殊的成功状态，用于处理data是null的情况
    data object Empty : ApiResult<Nothing>()
}

suspend fun <T> safeApiCall(call: suspend () -> IApiResponse<T>): ApiResult<T> {
    return try {
        val response = call()
        response.toResult()
    } catch (e: Exception) {
        logger("LogInterceptor") { "Net error：${e.localizedMessage ?: "Unknown error"}" }
        ApiResult.Error(0, e.localizedMessage ?: "Unknown error")
    }
}
