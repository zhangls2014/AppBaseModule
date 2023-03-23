package com.zhangls.base.retrofit

import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.zhangls.base.retrofit.common.ResponseException
import com.zhangls.base.retrofit.common.ResponseResult
import com.zhangls.base.util.LogTools
import retrofit2.HttpException

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
            combine()
        } catch (exception: HttpException) {
            exception.printStackTrace()
            LogTools.e("ResponseHandler", exception.toString())
            ResponseResult.Error(exception.code(), "请求异常[$exception]")
        } catch (exception: JsonSyntaxException) {
            exception.printStackTrace()
            LogTools.e("ResponseHandler", exception.toString())
            ResponseResult.Error(UNKNOWN_ERROR, "返回数据解析异常[$exception]")
        } catch (exception: MalformedJsonException) {
            exception.printStackTrace()
            LogTools.e("ResponseHandler", exception.toString())
            ResponseResult.Error(UNKNOWN_ERROR, "返回数据解析异常[$exception]")
        } catch (exception: ResponseException) {
            exception.printStackTrace()
            LogTools.e("ResponseHandler", exception.toString())
            ResponseResult.Error(exception.code, "请求异常[$exception]")
        } catch (exception: Exception) {
            exception.printStackTrace()
            LogTools.e("ResponseHandler", exception.toString())
            ResponseResult.Error(UNKNOWN_ERROR, "未知异常[$exception]")
        }
    }
}