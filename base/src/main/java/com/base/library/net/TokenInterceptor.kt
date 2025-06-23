package com.base.library.net

import com.base.library.utils.DataStoreKey
import com.base.library.utils.DatastoreUtil
import com.base.library.utils.Global
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor : Interceptor {

    private val dontNeedTokenUrls = listOf("/api/User/checkIn")

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        val requestPath = originalRequest.url.encodedPath

        // 如果请求路径在排除列表中，不添加 token
        if (dontNeedTokenUrls.any { requestPath.contains(it) }) {
            return chain.proceed(originalRequest)
        }

        val token = tokenProvider()
        val requestWithToken = originalRequest.newBuilder()
            .apply {
                addHeader("ba-user-token", token)
            }
            .build()

        return chain.proceed(requestWithToken)
    }

    private fun tokenProvider(): String {
        if (Global.token.isNotEmpty()) {
            return Global.token
        } else {
            val token = DatastoreUtil.read(DataStoreKey.TOKEN, "")
            if (token.isNotEmpty()) {
                Global.token = token
                return token
            } else {
                return ""
            }
        }
    }
}