package com.zhangls.base.retrofit

/**
 * 服务器请求成功后的返回 code 类型
 *
 * @author zhangls
 */
sealed class ResponseError {
    abstract val code: Int
    abstract val message: String

    data class Success(override val code: Int = 0, override val message: String) : ResponseError()
    data class UnknownError(override val code: Int = 1, override val message: String) : ResponseError()
    data class TokenTimeout(override val code: Int = 401, override val message: String) : ResponseError()
}
