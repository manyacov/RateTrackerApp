package com.manyacov.data.rate_tracker.datasource.remote.utils

import com.manyacov.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestWithHeaders = originalRequest.newBuilder()
            .addHeader("apikey", BuildConfig.RATE_TRACKER_API_KEY)
            .build()
        return chain.proceed(requestWithHeaders)
    }
}