@file:Suppress("unused")

package com.zhangls.base.extension

import android.text.style.URLSpan
import androidx.core.text.getSpans
import androidx.core.text.parseAsHtml
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException
import java.util.regex.Pattern

/**
 * 字符串工具类
 *
 * @author zhangls
 */

/**
 * 加密
 *
 * @param algorithm 加密方式，SHA-1、SHA-256、SHA-512、SHA-384、MD5 等
 * @return 加密之后的密文
 */
fun String.encrypt(algorithm: String): String {
    return try {
        // 获取 md5 工具对象
        val instance: MessageDigest = MessageDigest.getInstance(algorithm)
        // 对字符串加密，返回字节数组
        val digest: ByteArray = instance.digest(this.toByteArray())

        digest.toHexString
    } catch (e: NoSuchAlgorithmException) {
        e.printStackTrace()
        ""
    }
}

/**
 * byteArr 转 hexString
 */
val ByteArray.toHexString: String
    get() {
        val sb = StringBuffer()
        for (b in this) {
            // 获取低八位有效值
            val i: Int = b.toInt() and 0xff
            // 将整数转化为16进制
            var hexString = Integer.toHexString(i)
            if (hexString.length < 2) {
                // 如果是一位的话，补 0
                hexString = "0$hexString"
            }
            sb.append(hexString)
        }
        return sb.toString()
    }

/**
 * 判断一个字符串是否只含有数字
 */
val String.onlyContainDigit: Boolean
    get() = Pattern.compile("[0-9]+").matcher(this).matches()

/**
 * 判断一个字符串是否含有数字
 */
val String.containDigit: Boolean
    get() = Pattern.compile(".*[0-9].*").matcher(this).matches()

/**
 * 判断一个字符串是否只含有字母
 */
val String.onlyContainLetter: Boolean
    get() = Pattern.compile("[a-zA-Z]+").matcher(this).matches()

/**
 * 判断一个字符串是否含有字母
 */
val String.containLetter: Boolean
    get() = Pattern.compile(".*[a-zA-Z].*").matcher(this).matches()

/**
 * 将所有的标点符号替换为特定符号
 */
fun String.replaceAllSymbol(newValue: String): String {
    val regEx = "[`~!@#$%^&*()+=|{}':;,\\[\\].<>/?！￥…（）—【】‘；：”“’。， 、？]"
    return replace(regEx.toRegex(), newValue)
}

fun String.tipUrls(): List<String> {
    return parseAsHtml().getSpans<URLSpan>().map { it.url }
}