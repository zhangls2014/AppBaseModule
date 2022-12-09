package com.zhangls.base.retrofit.common

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.paradisehell.convex.converter.ConvexConverterFactory
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
        inline fun <reified T : Any> create(
            baseUrl: String,
            vararg interceptor: Interceptor,
            timeout: Long = 30,
            converter: ConvexConverterFactory? = null
        ): T {
            val clientBuilder = OkHttpClient.Builder()
                .connectTimeout(timeout, TimeUnit.SECONDS)
                .readTimeout(timeout, TimeUnit.SECONDS)
            interceptor.forEach {
                clientBuilder.addInterceptor(it)
            }
            val client = clientBuilder.build()

            return Retrofit.Builder().let {
                if (converter == null) {
                    it.baseUrl(baseUrl)
                        .client(client)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(T::class.java)
                } else {
                    it.baseUrl(baseUrl)
                        .client(client)
                        .addConverterFactory(converter)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(T::class.java)
                }
            }
        }
    }
}