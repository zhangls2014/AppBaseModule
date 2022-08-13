package com.zhangls.base.util

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.media.ThumbnailUtils
import android.os.Build
import android.provider.MediaStore


/**
 * @author zhangls
 */
class VideoUtils(private val videoPath: String) {
    private val retriever: MediaMetadataRetriever? = try {
        MediaMetadataRetriever().also {
            it.setDataSource(videoPath)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }


    /**
     * 获取视频时长
     */
    fun getVideoDuration(): Long {
        return retriever?.let {
            it.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)?.toLong() ?: 0
        } ?: 0
    }

    /**
     * 获取视频缩略图
     */
    fun getVideoThumbnail(width: Int = 640, height: Int = 350): Bitmap? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            retriever?.getScaledFrameAtTime(10L, MediaMetadataRetriever.OPTION_PREVIOUS_SYNC, width, height)
        } else {
          @Suppress("DEPRECATION")
          ThumbnailUtils.createVideoThumbnail(videoPath, MediaStore.Images.Thumbnails.MINI_KIND)
        }
    }

}