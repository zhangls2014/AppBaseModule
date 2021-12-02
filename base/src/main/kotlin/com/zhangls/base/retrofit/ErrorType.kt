package com.zhangls.base.retrofit

/**
 * 网络请求错误类型
 *
 * @author zhangls
 */
enum class ErrorType {
    // 网络出错
    NETWORK_ERROR,

    // 服务器访问异常
    SERVICE_ERROR,

    // 请求返回值异常
    RESPONSE_ERROR
}