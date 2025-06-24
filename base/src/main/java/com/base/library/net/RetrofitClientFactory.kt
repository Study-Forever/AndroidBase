package com.base.library.net

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientFactory {

    private var retrofitClient: Retrofit? = null

    fun create(baseUrl: String, interceptors: List<Interceptor> = emptyList()): Retrofit {
        return retrofitClient ?: createRetrofit(baseUrl, interceptors)
    }

    private fun createRetrofit(
        baseUrl: String,
        interceptors: List<Interceptor> = emptyList()
    ): Retrofit {
        retrofitClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient
                    .Builder()
                    .apply { interceptors.forEach { addInterceptor(it) } }
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        setLevel(HttpLoggingInterceptor.Level.BODY)
                    })
                    .build()
            )
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofitClient!!
    }
}