package com.zhangls.base.retrofit.common

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
        /**
         * @param timeout 本来是有默认参数，值为 30，但是因为与 vararg 搭配，导致必须使用具名参数形式，所以去掉了默认值
         */
        inline fun <reified T : Any> create(
            baseUrl: String,
            timeout: Long,
            vararg interceptor: Interceptor
        ): T {
            val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
            interceptor.forEach {
                clientBuilder.addInterceptor(it)
            }
            val client = clientBuilder.build()

            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(T::class.java)
        }
    }
}