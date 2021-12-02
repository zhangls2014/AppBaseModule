@file:Suppress("unused")

package com.zhangls.base.extension

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.widget.doOnTextChanged

private const val DECIMAL_PLACE = 2
private const val DECIMAL_POINT = "."

/**
 * @param place 小数位数限制
 */
fun EditText.decimalPlaceLimit(place: Int = DECIMAL_PLACE) {
    doOnTextChanged { text, _, _, _ ->
        text?.toString()?.let {
            var inputString: String

            // 限制输入内容，只能输入小数点和数字。
            // 防止没有设置 EditText 的 inputType=“numberDecimal”
            val value = charArrayOf('.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9')
            inputString = it.filter { c -> value.contains(c) }
            if (inputString != it) {
                this.setText(inputString)
                this.setSelection(inputString.length)

                return@doOnTextChanged
            }

            if (DECIMAL_POINT.contentEquals(inputString)) {
                // 如果直接输入「.」，则在「.」前追加「0」并将光标移至小数点后
                inputString = "0$inputString"
                this.setText(inputString)
                this.setSelection(inputString.length)

                return@doOnTextChanged
            }

            if (inputString.contains(DECIMAL_POINT)) {
                if (inputString.indexOf(DECIMAL_POINT) != inputString.lastIndexOf(DECIMAL_POINT)) {
                    // 如果已经输入了多个小数点了，则移除最后一个小数点及以后的内容
                    inputString = inputString.substring(0, inputString.lastIndexOf(DECIMAL_POINT))
                    this.setText(inputString)
                    this.setSelection(inputString.length)

                    return@doOnTextChanged
                }

                if (inputString.length - 1 - inputString.lastIndexOf(DECIMAL_POINT) > place) {
                    // 如果小数位数大于 DECIMAL_DIGITS，则直接删除后面多余的数字
                    inputString = inputString.substring(0, inputString.indexOf(DECIMAL_POINT) + place + 1)
                    this.setText(inputString)
                    this.setSelection(inputString.length)

                    return@doOnTextChanged
                }
            }

            if (inputString.startsWith("0") && inputString.length > 1) {
                // 当以 0 开始，但是还有其他数或者小数点的时候
                if (inputString.substring(1, 2) != DECIMAL_POINT) {
                    // 如果第二个字符不是小数点，则剪切掉后面的部分
                    this.setText(inputString.substring(0, 1))
                    this.setSelection(1)
                }
            }
        }
    }
}

/**
 * 打开软键盘
 */
fun EditText.openKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

/**
 * 关闭软键盘
 */
fun EditText.closeKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
}

/**
 * 切换输入法状态
 */
fun EditText.toggleKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_NOT_ALWAYS)
}