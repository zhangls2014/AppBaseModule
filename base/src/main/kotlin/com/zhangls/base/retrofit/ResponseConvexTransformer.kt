package com.zhangls.base.retrofit

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.MalformedJsonException
import com.zhangls.base.retrofit.common.ResponseException
import okio.IOException
import org.paradisehell.convex.transformer.ConvexTransformer
import java.io.InputStream


/**
 * @author zhangls
 */
class ResponseConvexTransformer : ConvexTransformer {
    private val gson = Gson()


    @Throws(IOException::class)
    override fun transform(original: InputStream): InputStream {
        try {
            val response = gson.fromJson<BaseResponse<JsonElement>>(
                original.reader(),
                object : TypeToken<BaseResponse<JsonElement>>() {}.type
            )
            return if (response.code == SUCCESS) {
                response.content.toString().byteInputStream()
            } else {
                throw ResponseException(response.code, "code: ${response.code}, message: ${response.message}")
            }
        } catch (exception: JsonSyntaxException) {
            exception.printStackTrace()
            throw JsonSyntaxException("返回数据解析异常[JsonSyntaxException]")
        } catch (exception: MalformedJsonException) {
            exception.printStackTrace()
            throw MalformedJsonException("接口数据解析异常[MalformedJsonException]")
        } catch (exception: ResponseException) {
            exception.printStackTrace()
            throw exception
        } catch (exception: Exception) {
            exception.printStackTrace()
            throw IOException(exception.message ?: "数据解析发生异常")
        }
    }
}