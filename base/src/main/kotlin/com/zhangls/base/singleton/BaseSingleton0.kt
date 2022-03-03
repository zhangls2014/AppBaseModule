package com.zhangls.base.singleton


/**
 * @author zhangls
 */
abstract class BaseSingleton0<out T> {
    @Volatile
    private var instance: T? = null

    protected abstract val creator: () -> T

    fun destroyInstance() {
        instance = null
    }

    fun getInstance(): T = instance ?: synchronized(this) {
        instance ?: creator().also { instance = it }
    }
}