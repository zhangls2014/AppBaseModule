package com.zhangls.base.retrofit

import com.google.gson.annotations.SerializedName

/**
 * 成功请求返回数据结构体
 *
 * @author zhangls
 */
data class BaseResponse<T>(
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("content") val content: T?
)
