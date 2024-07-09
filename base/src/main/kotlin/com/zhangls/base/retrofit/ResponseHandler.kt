package com.zhangls.base.retrofit

import com.blankj.utilcode.util.LogUtils
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.zhangls.base.retrofit.common.ResponseException
import com.zhangls.base.retrofit.common.ResponseResult
import retrofit2.HttpException
import java.net.UnknownHostException

/**
 * 统一对接口回调进行处理
 *
 * @author zhangls
 */
class ResponseHandler {

    /**
     * 对服务端返回的数据格式进行统一处理，并分类返回结果
     */
    suspend fun <Model : Any> responseHandler(combine: suspend () -> ResponseResult<Model>): ResponseResult<Model> {
        return try {
            val response = combine()
            response
        } catch (exception: Exception) {
            exception.printStackTrace()
            LogUtils.eTag("ResponseHandler", exception)

            val description = when (exception) {
                is HttpException, is ResponseException -> "请求异常"
                is JsonSyntaxException, is MalformedJsonException -> "返回数据解析异常"
                is UnknownHostException -> "未能找到使用指定主机名的服务器"
                else -> "未知异常"
            }

            ResponseResult.Error(UNKNOWN_ERROR, "$description[$exception]")
        }
    }
}