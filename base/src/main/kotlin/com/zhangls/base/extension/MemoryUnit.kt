package com.zhangls.base.extension

/**
 * @author zhangls
 */

enum class MemoryUnit(val size: Long) {
    /**
     * Byte与Byte的倍数
     */
    BYTE(1),

    /**
     * KB与Byte的倍数
     */
    KB(1024),

    /**
     * MB与Byte的倍数
     */
    MB(1048576),

    /**
     * GB与Byte的倍数
     */
    GB(1073741824)
}