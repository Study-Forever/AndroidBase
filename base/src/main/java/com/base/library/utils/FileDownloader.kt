package com.base.library.utils

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import androidx.core.content.ContextCompat
import java.io.File
import androidx.core.net.toUri
import kotlin.let
import kotlin.text.isBlank
import kotlin.text.lastIndexOf
import kotlin.text.substring

class FileDownloader {

    private var downloadId: Long = -1
    private var downloadCompleteReceiver: BroadcastReceiver? = null

    interface DownloadCallback {
        fun onSuccess(filePath: String)
        fun onFailure(error: String)
    }

    /**
     * 下载文件
     * @param url 文件下载URL
     * @param fileName 文件名
     * @param callback 文件下载结果回调
     */
    fun downloadFile(
        url: String,
        fileName: String,
        callback: DownloadCallback
    ) {
        // 检查URL是否有效
        if (url.isBlank()) {
            callback.onFailure("下载URL不能为空")
            return
        }
        // 处理文件名，确保有正确的后缀名
        val finalFileName = ensureFileExtension(fileName, url)
        // 创建下载请求
        val request = DownloadManager.Request(url.toUri())
            .setTitle(finalFileName)
            .setDescription("文件下载中...")
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, finalFileName)
            .setAllowedOverMetered(true)
            .setAllowedOverRoaming(true)

        // 获取DownloadManager服务
        val downloadManager =
            Global.cxt.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        // 注册下载完成广播接收器
        registerDownloadReceiver(downloadManager, callback, finalFileName)

        try {
            // 开始下载
            downloadId = downloadManager.enqueue(request)
        } catch (e: Exception) {
            callback.onFailure("下载失败: ${e.message}")
            unregisterDownloadReceiver()
        }
    }

    private fun registerDownloadReceiver(
        downloadManager: DownloadManager,
        callback: DownloadCallback,
        fileName: String
    ) {
        downloadCompleteReceiver = object : BroadcastReceiver() {
            @SuppressLint("Range")
            override fun onReceive(context: Context?, intent: Intent?) {
                try {
                    val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1) ?: -1
                    if (id == downloadId) {
                        val query = DownloadManager.Query().setFilterById(downloadId)
                        val cursor = downloadManager.query(query)

                        if (cursor.moveToFirst()) {
                            val status =
                                cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
                            when (status) {
                                DownloadManager.STATUS_SUCCESSFUL -> {
                                    // 直接使用我们设置的文件路径，避免从DownloadManager获取可能的问题
                                    val downloadDir =
                                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                                    val filePath = File(downloadDir, fileName).absolutePath

                                    // 检查文件是否存在
                                    if (File(filePath).exists()) {
                                        callback.onSuccess(filePath)
                                    } else {
                                        callback.onFailure("文件下载完成但无法找到: $filePath")
                                    }
                                }

                                DownloadManager.STATUS_FAILED -> {
                                    val reason =
                                        cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_REASON))
                                    callback.onFailure("下载失败，错误代码: $reason")
                                }
                            }
                        }
                        cursor.close()
                        unregisterDownloadReceiver()
                    }
                } catch (e: Exception) {
                    callback.onFailure("下载失败: ${e.message}")
                }
            }
        }

        ContextCompat.registerReceiver(
            Global.cxt,
            downloadCompleteReceiver,
            IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE),
            ContextCompat.RECEIVER_NOT_EXPORTED
        )
    }

    /**
     * 确保文件名有正确的后缀名
     * @param fileName 用户传入的文件名
     * @param url 下载URL
     * @return 带有正确后缀名的文件名
     */
    private fun ensureFileExtension(fileName: String, url: String): String {
        // 从URL中提取后缀名
        val extension = try {
            val path = url.toUri().lastPathSegment ?: ""
            val lastDotIndex = path.lastIndexOf('.')
            if (lastDotIndex != -1 && lastDotIndex < path.length - 1) {
                path.substring(lastDotIndex) // 包含点，如 ".pdf"
            } else {
                ""
            }
        } catch (_: Exception) {
            ""
        }

        return "$fileName$extension"
    }

    private fun unregisterDownloadReceiver() {
        downloadCompleteReceiver?.let {
            try {
                Global.cxt.unregisterReceiver(it)
            } catch (_: Exception) {
                // 接收器未注册，忽略
            }
            downloadCompleteReceiver = null
        }
    }
}