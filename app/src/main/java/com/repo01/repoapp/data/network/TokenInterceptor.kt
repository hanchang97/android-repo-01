package com.repo01.repoapp.data.network

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Singleton

@Singleton
class TokenInterceptor :Interceptor {
    private lateinit var token: String

    fun setToken(token : String) {
        this.token = token
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder()
                .header("Authorization", "token $token")
                .build()
        return chain.proceed(request)
    }
}