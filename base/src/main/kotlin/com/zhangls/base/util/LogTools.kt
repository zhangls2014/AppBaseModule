package com.zhangls.base.util

import android.content.Context
import android.os.Environment
import android.util.Log
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.GsonUtils
import com.zhangls.base.BuildConfig
import com.zhangls.base.util.LogTools.init
import glog.android.Glog
import timber.log.Timber
import java.io.File


/**
 * 日志工具，写入 Glog 日志的同时，也通过 [Timber] 输出到 Logcat
 *
 * 需要在应用启动时调用 [init] 方法
 *
 * @author zhangls
 */
object LogTools {
    private lateinit var glog: Glog

    init {
        val level = if (BuildConfig.DEBUG) {
            Glog.InternalLogLevel.InternalLogLevelDebug
        } else {
            Glog.InternalLogLevel.InternalLogLevelInfo
        }
        Glog.initialize(level)
    }

    fun init(applicationContext: Context) {
        glog = Glog.Builder(applicationContext)
            .protoName("glog-dualrecord")
            .rootDirectory(applicationContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)?.path)
            .encryptMode(Glog.EncryptMode.None)
            .incrementalArchive(true)
            .build()

        initTimber()
    }

    private fun initTimber() {
        if (Timber.forest().isEmpty()) {
            Timber.plant(Timber.DebugTree())
        }
    }

    fun i(tag: String, log: String) {
        Timber.i("$tag, $log")
        write(LogProtos(Log.INFO, tag, log))
    }

    fun d(tag: String, log: String) {
        Timber.d("$tag, $log")
        write(LogProtos(Log.DEBUG, tag, log))
    }

    fun e(tag: String, log: String) {
        Timber.e("$tag, $log")
        write(LogProtos(Log.ERROR, tag, log))
    }

    fun v(tag: String, log: String) {
        Timber.v("$tag, $log")
        write(LogProtos(Log.VERBOSE, tag, log))
    }

    fun w(tag: String, log: String) {
        Timber.w("$tag, $log")

        write(LogProtos(Log.WARN, tag, log))
    }

    fun write(level: Int, tag: String, log: String) {
        write(LogProtos(level, tag, log))
    }

    private fun write(logProtos: LogProtos) {
        if (::glog.isInitialized) {
            glog.write(GsonUtils.toJson(logProtos).toByteArray())
        }
    }

    fun flush() {
        if (::glog.isInitialized) {
            glog.flush()
        }
    }

    fun getArchiveSnapshot(): List<String> {
        return if (::glog.isInitialized.not()) {
            emptyList()
        } else {
            arrayListOf<String>().also {
                glog.getArchiveSnapshot(it, 0, 0)
            }
        }
    }

    fun convertToJsonFile(path: String, outputPath: String) {
        runCatching {
            glog.openReader(path)
        }.onSuccess { reader ->
            val inBuf = ByteArray(Glog.getSingleLogMaxLength())

            FileUtils.createOrExistsFile(outputPath)
            File(outputPath).outputStream().use {
                it.write("[".toByteArray())
                var logCount: Long = 0
                while (true) {
                    val count = reader.read(inBuf)
                    if (count < 0) { // 读取结束
                        break
                    } else if (count == 0) { // 破损恢复
                        continue
                    }

                    if (logCount > 0) {
                        it.write(",".toByteArray())
                    }
                    logCount += 1

                    val content = inBuf.copyOfRange(0, count)
                    it.write(String(content).toByteArray())
                }
                it.write("]".toByteArray())
            }
        }
    }


    private data class LogProtos(
        val logLevel: Int,
        val tag: String,
        val message: String,
        val timestamp: Long = System.currentTimeMillis(),
        val tid: Long = Thread.currentThread().id,
        val pid: Int = android.os.Process.myPid()
    )

}