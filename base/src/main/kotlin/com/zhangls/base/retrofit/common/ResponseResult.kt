package com.zhangls.base.retrofit.common

/**
 * 接口请求返回结果
 *
 * @author zhangls
 */
sealed class ResponseResult<out T : Any> {
    data class Success<out T : Any>(val message: String?, val data: T) : ResponseResult<T>()

    data class Error(val code: Int, val message: String) : ResponseResult<Nothing>()
}