package com.zhangls.base.retrofit

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.MalformedJsonException
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
            if (response.code == SUCCESS) {
                return response.content.toString().byteInputStream()
            } else {
                throw IOException(response.message)
            }
        } catch (exception: JsonSyntaxException) {
            exception.printStackTrace()
            throw IOException("返回数据解析异常[JsonSyntaxException]")
        } catch (exception: MalformedJsonException) {
            exception.printStackTrace()
            throw IOException("接口数据解析异常[MalformedJsonException]")
        } catch (exception: Exception) {
            exception.printStackTrace()
            throw IOException(exception.message ?: "数据解析发生异常")
        }
    }
}