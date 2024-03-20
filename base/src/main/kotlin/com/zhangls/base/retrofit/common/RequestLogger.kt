package com.zhangls.base.retrofit.common

import com.blankj.utilcode.util.LogUtils
import okhttp3.logging.HttpLoggingInterceptor

/**
 * 将请求日志打印到控制台或者日志文件中
 *
 * @author zhangls
 */
class RequestLogger : HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        LogUtils.i(message)
    }
}