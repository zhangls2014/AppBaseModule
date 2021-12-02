package com.zhangls.base.retrofit

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.ResponseBody
import java.io.File

/**
 * @author zhangls
 */
fun ResponseBody.downloadFileWithProgress(file: File): Flow<Float> = flow {
    emit(0F)

    byteStream().use { inputStream ->
        file.outputStream()
            .use { outputStream ->
                var bytesCopied: Long = 0
                val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
                var bytes = inputStream.read(buffer)
                while (bytes >= 0) {
                    outputStream.write(buffer, 0, bytes)
                    bytesCopied += bytes
                    bytes = inputStream.read(buffer)
                    emit(bytesCopied.toFloat() / contentLength())
                }
            }
    }
}