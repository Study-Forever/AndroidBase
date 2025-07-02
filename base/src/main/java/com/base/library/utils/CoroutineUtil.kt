package com.base.library.utils

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    logger("DatastoreUtilException") { "Caught unhandled exception: $throwable" }
    // 这里可以添加你的异常处理逻辑，如日志记录、错误上报等
}
val safeScope = CoroutineScope(Dispatchers.IO + exceptionHandler)