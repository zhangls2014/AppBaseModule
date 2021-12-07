package com.zhangls.base.retrofit

/**
 * 接口请求返回结果
 *
 * @author zhangls
 */
sealed class ResponseResult<out T : Any> {
    data class Success<out T : Any>(val data: T) : ResponseResult<T>()
    data class Error(val error: ResponseError) : ResponseResult<Nothing>()
}