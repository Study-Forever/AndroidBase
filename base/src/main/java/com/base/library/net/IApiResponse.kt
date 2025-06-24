package com.base.library.net

interface IApiResponse<T> {
    fun toResult(): ApiResult<T>
}