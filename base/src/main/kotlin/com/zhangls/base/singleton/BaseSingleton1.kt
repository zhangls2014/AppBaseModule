package com.zhangls.base.singleton


/**
 * @author zhangls
 */
abstract class BaseSingleton1<in P, out T> {
    @Volatile
    private var instance: T? = null

    protected abstract val creator: (P) -> T

    fun destroyInstance() {
        instance = null
    }

    fun getInstance(param: P): T = instance ?: synchronized(this) {
        instance ?: creator(param).also { instance = it }
    }
}