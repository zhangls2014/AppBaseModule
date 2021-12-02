package com.zhangls.base.retrofit

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Retrofit 实例
 *
 * @author zhangls
 */
class RetrofitService {
    companion object {
        fun <T> create(baseUrl: String, service: Class<T>, vararg interceptor: Interceptor, timeout: Long = 30): T {
            val builder = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
            interceptor.forEach {
                builder.addInterceptor(it)
            }
            val client = builder.build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(service)
        }
    }
}