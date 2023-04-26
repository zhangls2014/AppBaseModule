package com.zhangls.base.util

import androidx.annotation.IntRange


/**
 * @author zhangls
 */
object Debugs {
    fun stackTrace(self: Any, @IntRange(from = 2) limit: Int = 3): String {
        val result = StringBuilder()
        var length = 0
        for ((i, it) in Thread.currentThread().stackTrace.withIndex()) {
            if (i < 4 || it.methodName.contains("\$default")) continue
            if (length > 0) result.append(" <- ")
            if (self.javaClass.name != it.className) {
                result.append(it.className).append(".")
            }
            result.append(it.methodName)
            length++
            if (length >= limit) break
        }
        return result.toString()
    }

    fun callName(): String {
        return Thread.currentThread()
            .stackTrace
            .let { element ->
                val index = element.indexOfLast { it.className == this::class.java.name }
                if (element.size <= index + 1) {
                    element[index]
                } else {
                    element[index + 1]
                }
            }?.let { traceElement ->
                return "${traceElement.className}.${traceElement.methodName}"
            } ?: "null"
    }
}