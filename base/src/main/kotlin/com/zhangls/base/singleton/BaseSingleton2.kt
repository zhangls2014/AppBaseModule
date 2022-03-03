package com.zhangls.base.singleton


/**
 * @author zhangls
 */
abstract class BaseSingleton2<in P1, in P2, out T> {
    @Volatile
    private var instance: T? = null

    protected abstract val creator: (P1, P2) -> T

    fun destroyInstance() {
        instance = null
    }

    fun getInstance(param1: P1, param2: P2): T = instance ?: synchronized(this) {
        instance ?: creator(param1, param2).also { instance = it }
    }
}