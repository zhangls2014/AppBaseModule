package com.zhangls.base.extension

import android.graphics.Bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream

/**
 * @author zhangls
 */

/**
 * bitmap 转 base64（后端爱用的上传文件方式）
 */
fun Bitmap.toBase64(): String {
    return ByteArrayOutputStream().use {
        compress(Bitmap.CompressFormat.JPEG, 100, it)
        Base64.encodeToString(it.toByteArray(), Base64.DEFAULT)
    }
}