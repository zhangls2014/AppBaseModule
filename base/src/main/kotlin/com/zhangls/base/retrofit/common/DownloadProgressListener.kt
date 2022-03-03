package com.zhangls.base.retrofit.common

/**
 * 下载进度回调
 *
 * @author zhangls
 */
fun interface DownloadProgressListener {
    fun onProgress(progress: Float)
}