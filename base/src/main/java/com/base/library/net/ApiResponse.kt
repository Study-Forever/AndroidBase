package com.base.library.net

data class ApiResponse<T>(
    val code: Int,
    val msg: String,
    val data: T?
)
