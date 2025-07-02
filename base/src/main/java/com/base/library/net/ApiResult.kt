package com.base.library.net

import com.base.library.utils.logger

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()
    data class NoData(val code: Int) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}

suspend fun <T> safeApiCall(call: suspend () -> IApiResponse<T>): ApiResult<T> {
    return try {
        val response = call()
        response.toResult()
    } catch (e: Exception) {
        logger("LogInterceptor") { "网络请求出错：${e.localizedMessage ?: "Unknown error"}" }
        ApiResult.Error(0, e.localizedMessage ?: "Unknown error")
    }
}
