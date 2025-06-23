package com.base.library.net

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClientFactory {

    private var retrofitClient: Retrofit? = null

    fun get(baseUrl: String): Retrofit {
        return retrofitClient ?: createRetrofit(baseUrl)
    }

    private fun createRetrofit(baseUrl: String) : Retrofit {
        retrofitClient = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(
                OkHttpClient
                    .Builder()
                    .addInterceptor(TokenInterceptor())
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