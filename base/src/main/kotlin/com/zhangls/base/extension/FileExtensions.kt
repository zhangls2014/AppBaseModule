@file:Suppress("unused")

package com.zhangls.base.extension

import android.app.usage.StorageStatsManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Environment
import android.os.StatFs
import android.os.storage.StorageManager
import androidx.annotation.IntRange
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.ByteArrayOutputStream
import java.io.Closeable
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.RandomAccessFile
import java.math.RoundingMode
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @author zhangls
 */

fun Closeable.closeTryCatch() {
    try {
        close()
    } catch (e: IOException) {
        throw IOException("IOException occurred. ", e)
    }
}

fun Closeable.closeQuietly() {
    try {
        close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

fun ByteArrayOutputStream.closeQuietly() {
    try {
        close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

/**
 * 保存文本
 *
 * @param content  内容
 * @param append   是否累加
 * @return 是否成功
 */
fun File.saveTextValue(content: String, append: Boolean): Boolean {
    return try {
        val os = FileOutputStream(this, append)
        os.write(content.toByteArray(charset("UTF-8")))
        os.closeTryCatch()
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

/**
 * 字节数转以unit为单位的size
 *
 * @param unit
 *  * [MemoryUnit.BYTE]: 字节
 *  * [MemoryUnit.KB]  : 千字节
 *  * [MemoryUnit.MB]  : 兆
 *  * [MemoryUnit.GB]  : GB
 *
 * @return 以 unit 为单位的 size
 */
fun Long.byteToSize(unit: MemoryUnit): Double {
    return when (unit) {
        MemoryUnit.BYTE -> this.toDouble() / MemoryUnit.BYTE.size
        MemoryUnit.KB -> this.toDouble() / MemoryUnit.KB.size
        MemoryUnit.MB -> this.toDouble() / MemoryUnit.MB.size
        MemoryUnit.GB -> this.toDouble() / MemoryUnit.GB.size
    }
}

/**
 * 生成分片文件的路径
 *
 * 路径为需要分片的文件同名文件夹下 + "_index(分片序号)" + suffix
 */
fun File.makeBlockFile(index: Int): String {
    if (exists().not()) {
        throw Exception("原文件不存在")
    }

    val nameSuffixIndex = name.lastIndexOf(".")
    val blockName = name.substring(0, nameSuffixIndex) + "_" + index + name.substring(
        nameSuffixIndex,
        name.length
    )

    var basePath: String? = parent
    if (basePath == null) {
        basePath = File.separator
    } else {
        if (basePath.endsWith(File.separator).not()) {
            basePath += File.separator
        }
    }

    basePath?.let {
        val baseFile = File(basePath)
        if (baseFile.exists().not() || baseFile.isFile) {
            baseFile.mkdirs()
        }
        return basePath + File.separator + blockName
    }

    return ""
}

/**
 * 获取分片文件
 */
fun File.getBlockFile(fileBlockSize: Int, index: Int): File {
    if (exists().not()) {
        throw Exception("原文件不存在")
    }

    val blockFilePath = makeBlockFile(index)
    val blockFile = File(blockFilePath)

    if (blockFile.exists() && blockFile.length() > 0) {
        return blockFile
    }

    try {
        blockFile.createNewFile()
    } catch (e: IOException) {
        throw Exception("创建分片文件失败,path:[" + blockFilePath + "]" + e.message, e)
    }

    if (blockFile.exists().not()) {
        throw Exception("分片文件不存在")
    }

    var raf: RandomAccessFile? = null
    var out: RandomAccessFile? = null
    try {
        raf = RandomAccessFile(this, "r")
        out = RandomAccessFile(blockFile, "rw")
        val seek = fileBlockSize * index
        raf.seek(seek.toLong())
        val data = ByteArray(fileBlockSize)
        val count = raf.read(data)
        out.write(data, 0, count)
    } catch (e: Exception) {
        throw Exception("保存分片文件失败:" + e.message, e)
    } finally {
        raf?.closeTryCatch()
        out?.closeTryCatch()
    }
    return blockFile
}


/**
 * 获取文件的MD5校验码
 *
 * @return 文件的 MD5 校验码
 */
val File.encodeMD5: String
    get() {
        var fileInputStream: FileInputStream? = null
        try {
            val md = MessageDigest.getInstance("MD5")
            val buffer = ByteArray(2048)
            fileInputStream = FileInputStream(this)

            var read = fileInputStream.read(buffer)
            while (read > 0) {
                md.update(buffer, 0, read)
                read = fileInputStream.read(buffer)
            }

            return md.digest().toHexString
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            fileInputStream?.closeQuietly()
        }
        return ""
    }


/**
 * 获取空闲的存储空间字节
 */
suspend fun Context.getFreeBytes(): Long {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val storageStatsManager =
            getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
        storageStatsManager.getFreeBytes(StorageManager.UUID_DEFAULT)
    } else {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val path = Environment.getExternalStorageDirectory()
            val sf = StatFs(path.path)
            // 获取单个数据块的大小(Byte)
            val blockSize = sf.blockSizeLong
            // 空闲的数据块的数量
            val freeBlocks = sf.freeBlocksLong
            // 返回 SD 卡空闲大小
            freeBlocks * blockSize // 单位 Byte
        } else {
            0
        }
    }
}

/**
 * 获取空闲的存储空间字节
 */
suspend fun Context.getTotalBytes(): Long {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val storageStatsManager = getSystemService(Context.STORAGE_STATS_SERVICE) as StorageStatsManager
        storageStatsManager.getTotalBytes(StorageManager.UUID_DEFAULT)
    } else {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val path = Environment.getExternalStorageDirectory()
            val sf = StatFs(path.path)
            // 获取单个数据块的大小(Byte)
            val blockSize = sf.blockSizeLong
            // 总的数据块的数量
            val freeBlocks = sf.totalBytes
            // 返回 SD 卡总的大小
            freeBlocks * blockSize // 单位 Byte
        } else {
            0
        }
    }
}

/**
 * 获取空闲的存储空间字节
 */
suspend fun Context.getAllocatableBytes(): Long {
    return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        val storageManager = getSystemService(Context.STORAGE_SERVICE) as StorageManager
        storageManager.getAllocatableBytes(StorageManager.UUID_DEFAULT)
    } else {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            val path = Environment.getExternalStorageDirectory()
            val sf = StatFs(path.path)
            // 获取单个数据块的大小(Byte)
            val blockSize = sf.blockSizeLong
            // 可用的数据块的数量
            val freeBlocks = sf.availableBlocksLong
            // 返回 SD 卡可用大小
            freeBlocks * blockSize // 单位 Byte
        } else {
            0
        }
    }
}

/**
 * 获取文件大的的粗略表述
 *
 * @param length 小数位数
 */
fun Long.getFileSizeString(@IntRange(from = 0, to = 3) length: Int): String {
    return when {
        this < MemoryUnit.KB.size -> {
            // 不足 1KB
            "${this}Byte"
        }
        (this.toDouble() / MemoryUnit.KB.size) < 1024 -> {
            // 不足 1MB
            val toFloat = (this.toDouble() / MemoryUnit.KB.size).toBigDecimal()
                .setScale(length, RoundingMode.HALF_UP)
                .toFloat()
            "${toFloat}KB"
        }
        (this.toDouble() / MemoryUnit.MB.size) < 1024 -> {
            // 不足 1GB
            val toFloat = (this.toDouble() / MemoryUnit.MB.size).toBigDecimal()
                .setScale(length, RoundingMode.HALF_UP)
                .toFloat()
            "${toFloat}MB"
        }
        else -> {
            // 超过 1GB
            val toFloat = (this.toDouble() / MemoryUnit.GB.size).toBigDecimal()
                .setScale(length, RoundingMode.HALF_UP)
                .toFloat()
            "${toFloat}GB"
        }
    }
}

/**
 * 将 bitmap 保存到 File 中
 */
fun File.saveBitmap(bitmap: Bitmap) {
    BufferedOutputStream(FileOutputStream(this)).use {
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, it)
    }
}

/**
 * 从照片文件中读取 bitmap
 */
fun File.readImage(): Bitmap {
    return BufferedInputStream(FileInputStream(this)).use {
        BitmapFactory.decodeStream(it)
    }
}