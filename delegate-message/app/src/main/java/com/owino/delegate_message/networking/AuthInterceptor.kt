package com.owino.delegate_message.networking
import okhttp3.Interceptor
import okhttp3.Response
class AuthInterceptor(private val apiKey: String): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val interceptChain = chain.request()
            .newBuilder()
            .addHeader("Accept","application/json")
            .addHeader("Authorization:",apiKey)
            .build();
        return chain.proceed(interceptChain)
    }
}