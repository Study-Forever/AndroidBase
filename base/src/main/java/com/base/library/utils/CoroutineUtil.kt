package com.base.library.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    logger("CoroutineException") { "Caught unhandled exception: $throwable" }
    // 这里可以添加你的异常处理逻辑，如日志记录、错误上报等
}
val safeIOScope = CoroutineScope(Dispatchers.IO + exceptionHandler)
val safeMainScope = CoroutineScope(Dispatchers.Main + exceptionHandler)