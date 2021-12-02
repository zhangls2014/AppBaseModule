@file:Suppress("unused")

package com.zhangls.base.extension

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.IntRange
import kotlin.math.roundToInt

/**
 * @author zhangls
 */

/**
 * dp 转 px
 */
val Number.dpFloat: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        Resources.getSystem().displayMetrics
    )

/**
 * dp 转 px
 */
val Number.dp: Int
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        toFloat(),
        Resources.getSystem().displayMetrics
    ).roundToInt()

/**
 * px 转 dp
 */
val Int.pxToDp: Float get() = this / Resources.getSystem().displayMetrics.density

/**
 * sp 转 px
 */
val Int.spToPxFloat: Float
    get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        toFloat(),
        Resources.getSystem().displayMetrics
    )

/**
 * 返回粗略的数值，添加 k, m, b 后缀
 */
fun Long.toRough(@IntRange(from = 1, to = 3) decimalPlace: Int): String {
    val suffix = arrayOf("K", "M", "B")
    // 将长整数除以 1000.0，得到双精度浮点数
    var d = this / 1000.0

    return if (d < 1) {
        this.toString()
    } else if (d < 1000) {
        String.format("%.${decimalPlace}f", d) + suffix[0]
    } else {
        d /= 1000.0
        if (d < 1000) {
            String.format("%.${decimalPlace}f", d) + suffix[1]
        } else {
            String.format("%.${decimalPlace}f", d / 1000.0) + suffix[2]
        }
    }
}