package com.zhangls.base.util

import android.content.Context
import android.os.Environment
import com.blankj.utilcode.util.FileUtils
import com.google.gson.GsonBuilder
import com.zhangls.base.BuildConfig
import com.zhangls.base.LogProtos
import com.zhangls.base.util.LogTools.init
import glog.android.Glog
import timber.log.Timber
import java.io.File
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.atomic.AtomicLong


/**
 * 日志工具，写入 Glog 日志的同时，也通过 [Timber] 输出到 Logcat
 *
 * 需要在应用启动时调用 [init] 方法
 *
 * @author zhangls
 */
object LogTools {
    private lateinit var glog: Glog

    // 序号
    private val seq = AtomicLong()

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
        write(serializeLog(LogProtos.Log.Level.INFO, tag, log))
    }

    fun d(tag: String, log: String) {
        Timber.d("$tag, $log")
        write(serializeLog(LogProtos.Log.Level.DEBUG, tag, log))
    }

    fun e(tag: String, log: String) {
        Timber.e("$tag, $log")
        write(serializeLog(LogProtos.Log.Level.ERROR, tag, log))
    }

    fun v(tag: String, log: String) {
        Timber.v("$tag, $log")
        write(serializeLog(LogProtos.Log.Level.VERBOSE, tag, log))
    }

    fun w(tag: String, log: String) {
        Timber.w("$tag, $log")

        write(serializeLog(LogProtos.Log.Level.WARN, tag, log))
    }

    fun write(level: LogProtos.Log.Level, tag: String, log: String) {
        write(serializeLog(level, tag, log))
    }

    private fun write(logProtos: ByteArray) {
        if (::glog.isInitialized) {
            glog.write(logProtos)
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
                    it.write(deserializeLog(content).toByteArray())
                }
                it.write("]".toByteArray())
            }
        }
    }

    private fun serializeLog(level: LogProtos.Log.Level, tag: String, message: String): ByteArray {
        return LogProtos.Log.newBuilder()
            .setLogLevel(level)
            .setSequence(seq.getAndIncrement())
            .setTimestamp(System.currentTimeMillis().toString())
            .setPid(android.os.Process.myPid())
            .setTid(Thread.currentThread().id.toString())
            .setTag(tag)
            .setMsg(message)
            .build()
            .toByteArray()
    }

    private fun deserializeLog(bytes: ByteArray): String {
        return LogProtos.Log.parseFrom(bytes).string()
    }

    private fun LogProtos.Log.string(): String {
        return LogFormatter(
            sequence,
            SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date(timestamp.toLong())),
            logLevel.name,
            pid,
            tid,
            tag,
            msg
        ).let {
            GsonBuilder().disableHtmlEscaping()
                .setPrettyPrinting()
                .create()
                .toJson(it)
        }
    }

    private data class LogFormatter(
        val sequence: Long,
        val time: String,
        val logLevel: String,
        val pid: Int,
        val tid: String,
        val tag: String,
        val msg: String
    )

}