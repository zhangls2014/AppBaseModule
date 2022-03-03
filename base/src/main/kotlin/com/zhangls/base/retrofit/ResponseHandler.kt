package com.zhangls.base.retrofit

import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import com.zhangls.base.retrofit.common.ResponseResult
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
            ResponseResult.Error(
                code = exception.code(),
                message = "请求异常[${exception.javaClass.simpleName} = ${exception.cause}]"
            )
        } catch (exception: JsonSyntaxException) {
            exception.printStackTrace()
            ResponseResult.Error(
                code = UNKNOWN_ERROR,
                message = "返回数据解析异常[${exception.javaClass.simpleName} = ${exception.cause}]"
            )
        } catch (exception: MalformedJsonException) {
            exception.printStackTrace()
            ResponseResult.Error(
                code = UNKNOWN_ERROR,
                message = "返回数据解析异常[${exception.javaClass.simpleName} = ${exception.cause}]"
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            ResponseResult.Error(
                code = UNKNOWN_ERROR,
                message = "未知异常[${exception.javaClass.simpleName} = ${exception.cause}]"
            )
        }
    }
}