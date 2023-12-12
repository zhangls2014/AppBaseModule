package com.zhangls.base.data

/**
 * 提示消息
 *
 * 在这里定义了四种种提示消息的形式：Dialog、SnackBar、Toast、Loading
 *
 * @author zhangls
 */
sealed class TipsEvent {
    data class ShowDialog(
        val title: String,
        val content: String,
        val positiveText: String,
        val negativeText: String? = null,
        // 用于标注 Dialog 事件的类型
        val action: Int = 0
    ) : TipsEvent()

    data class SnackBar(val text: String, val shortTime: Boolean = true) : TipsEvent()
    data class Toast(val text: String, val shortTime: Boolean = true) : TipsEvent()
    data class Loading(val display: Boolean, val text: String = "加载中") : TipsEvent()
}

fun String.snack(shortTime: Boolean) = TipsEvent.SnackBar(this, shortTime)

fun String.toast(shortTime: Boolean) = TipsEvent.Toast(this, shortTime)

fun String.loading(display: Boolean) = TipsEvent.Loading(display, this)
