package com.zhangls.base.data

import androidx.lifecycle.LiveData

/**
 * [LiveData] 数据封装类，以此来区分一次性消费数据，持续性消费数据
 *
 * https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
 *
 * @author zhangls
 */
@Deprecated("LiveDataEvent 已经不推荐使用，可以使用 Flow 更灵活的订阅数据")
class LiveDataEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}