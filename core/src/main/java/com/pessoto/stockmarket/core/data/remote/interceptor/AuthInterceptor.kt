package com.pessoto.stockmarket.core.data.remote.interceptor

import com.pessoto.stockmarket.core.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newUrl = chain.request().url.newBuilder()
            .addQueryParameter("token", BuildConfig.API_KEY)
            .build()

        val newRequest = chain.request().newBuilder()
            .url(newUrl)
            .build()

        return chain.proceed(newRequest)
    }
}
