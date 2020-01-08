package com.diswy.foundation.tools

import android.app.DownloadManager
import android.app.DownloadManager.Request
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import androidx.core.net.toUri
import com.diswy.foundation.quick.toast
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateHelper @Inject constructor() {

    private val mDownloadReceiver = DownloadReceiver()

    private lateinit var mDownloadManager: DownloadManager

    private var mDownloadTaskId: Long = 0L

    private inner class DownloadReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            context ?: return
            intent ?: return

            println("-----------!@#->DownloadReceiver:动作字符串=${intent.action}")
            when (intent.action) {
                DownloadManager.ACTION_DOWNLOAD_COMPLETE -> {
                    println("----------!@#->下载成功")
                }
                DownloadManager.ACTION_NOTIFICATION_CLICKED -> {
                    println("----------!@#->点击了！！！")

                }
            }

        }
    }

    fun download(context: Context) {
        context.toast("开始下载")
        val request =
            Request("https://ebdexstyle.oss-cn-hangzhou.aliyuncs.com/diandian_android_v1.3.3.apk".toUri())
        request.setNotificationVisibility(Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
        request.setTitle("版本更新")
        request.setVisibleInDownloadsUi(true)
        request.setMimeType("application/vnd.android.package-archive")

        val file =
            File(context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "1.apk")
        if (file.exists()) {
            file.delete()
        }
        request.setDestinationUri(file.toUri())

        mDownloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        mDownloadTaskId = mDownloadManager.enqueue(request)

        println("-----------!@#->UpdateHelper:context=$context")
        println("-----------!@#->UpdateHelper:mDownloadTaskId=$mDownloadTaskId")
        println("-----------!@#->UpdateHelper:file=${file.absolutePath}")
        val intentFilter = IntentFilter()
        intentFilter.addAction(DownloadManager.ACTION_DOWNLOAD_COMPLETE)
        intentFilter.addAction(DownloadManager.ACTION_NOTIFICATION_CLICKED)
        context.registerReceiver(
            mDownloadReceiver,
            intentFilter
        )
    }
}