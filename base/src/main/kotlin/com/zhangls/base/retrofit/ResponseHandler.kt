package com.zhangls.base.retrofit

import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
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
                code = HttpCode.UnknownError(message = "请求异常[${exception.javaClass.simpleName} = ${exception.cause}]"),
                type = ErrorType.SERVICE_ERROR
            )
        } catch (exception: JsonSyntaxException) {
            exception.printStackTrace()
            ResponseResult.Error(
                code = HttpCode.UnknownError(message = "返回数据解析异常[${exception.javaClass.simpleName} = ${exception.cause}]"),
                type = ErrorType.RESPONSE_ERROR
            )
        } catch (exception: MalformedJsonException) {
            exception.printStackTrace()
            ResponseResult.Error(
                code = HttpCode.UnknownError(message = "返回数据解析异常[${exception.javaClass.simpleName} = ${exception.cause}]"),
                type = ErrorType.RESPONSE_ERROR
            )
        } catch (exception: Exception) {
            exception.printStackTrace()
            ResponseResult.Error(
                code = HttpCode.UnknownError(message = "未知异常[${exception.javaClass.simpleName} = ${exception.cause}]"),
                type = ErrorType.NETWORK_ERROR
            )
        }
    }
}