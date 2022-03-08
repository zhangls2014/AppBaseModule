package com.zhangls.base.retrofit.common

/**
 * @author zhangls
 */
class ResponseException(val code: Int, message: String) : RuntimeException(message)