package com.base.library.net

import com.base.library.utils.ResultCode
import com.base.library.utils.logger

sealed class ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()
    data object Loading : ApiResult<Nothing>()
}

/**
 * 有些接口请求成功code为1，但是data为null，调用方在判断是否成功时应优先以code是否等于1来判断是否成功
 */
fun <T> ApiResponse<T>.toResult(): ApiResult<T> {
    return if (code == ResultCode.SUCCESS) {
        data?.let { ApiResult.Success(it) } ?: ApiResult.Error(code, "Data is null")
    } else {
        ApiResult.Error(code, msg)
    }
}

suspend fun <T> safeApiCall(call: suspend () -> ApiResponse<T>): ApiResult<T> {
    return try {
        val response = call()
        response.toResult()
    } catch (e: Exception) {
        logger("LogInterceptor") { "网络请求出错：${e.localizedMessage ?: "Unknown error"}" }
        ApiResult.Error(0, e.localizedMessage ?: "Unknown error")
    }
}
