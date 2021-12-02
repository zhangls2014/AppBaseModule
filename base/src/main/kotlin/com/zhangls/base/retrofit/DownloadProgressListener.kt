package com.zhangls.base.retrofit

/**
 * 下载进度回调
 *
 * @author zhangls
 */
fun interface DownloadProgressListener {
    fun onProgress(progress: Float)
}