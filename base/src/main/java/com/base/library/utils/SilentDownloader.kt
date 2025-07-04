package com.base.library.utils

import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import kotlin.io.copyTo
import kotlin.io.use
import kotlin.let
import kotlin.toString

object SilentDownloader {

    interface DownloadCallback {
        fun onSuccess(filePath: String)
        fun onFailure(error: String)
    }

    fun startDownload(url: String, fileName: String, callback: DownloadCallback) {
        try {
            val request = Request.Builder()
                .url(url)
                .build()

            OkHttpClient().newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    callback.onFailure(e.message ?: "")
                }

                override fun onResponse(call: Call, response: Response) {
                    if (!response.isSuccessful) {
                        callback.onFailure("下载失败")
                        return
                    }

                    response.body?.let { body ->
                        try {
                            // 创建目标文件
                            val file = File(Global.cxt.filesDir, fileName)

                            // 确保目录存在
                            if (file.parentFile?.exists() == false) {
                                file.parentFile?.mkdirs()
                            }

                            // 写入文件
                            FileOutputStream(file).use { output ->
                                body.byteStream().use { input ->
                                    input.copyTo(output)
                                }
                            }

                            // 返回成功结果
                            callback.onSuccess(file.absolutePath)
                        } catch (e: Exception) {
                            callback.onFailure(e.message.toString())
                        } finally {
                            response.close()
                        }
                    } ?: run {
                        callback.onFailure("Response body is null")
                    }
                }
            })
        } catch (e: Exception) {
            callback.onFailure(e.message.toString())
        }
    }
}