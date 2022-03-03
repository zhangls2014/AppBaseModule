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
        val negativeText: String?
    ) : TipsEvent()

    data class SnackBar(val text: String, val shortTime: Boolean = true) : TipsEvent()
    data class Toast(val text: String, val shortTime: Boolean = true) : TipsEvent()
    data class Loading(val display: Boolean, val text: String = "加载中") : TipsEvent()
}
