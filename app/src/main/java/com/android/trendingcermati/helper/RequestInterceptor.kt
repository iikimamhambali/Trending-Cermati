package com.android.trendingcermati.helper

import com.android.trendingcermati.network.Param
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class RequestInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newRequest = addCommonQueryParam(request)
        return chain.proceed(newRequest)
    }

    fun addCommonQueryParam(request: Request): Request {
        val httpUrl = request.url().newBuilder()
            .addQueryParameter(Param.SORT, Param.SORT_ASC)
            .build()
        return request.newBuilder().url(httpUrl).build()
    }
}