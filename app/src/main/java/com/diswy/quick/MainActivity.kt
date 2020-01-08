package com.diswy.quick

import android.Manifest
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.net.toUri
import com.diswy.common.App
import com.diswy.common.viewmodel.CommonViewModel
import com.diswy.foundation.base.Permission
import com.diswy.foundation.base.activity.BaseActivity
import com.diswy.foundation.quick.toast
import com.diswy.foundation.receiver.DownAndInstallReceiver
import com.diswy.foundation.tools.UpdateHelper
import com.google.gson.Gson
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import java.io.File
import javax.inject.Inject

class MainActivity : BaseActivity() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initialize() {
//        DaggerActivityComponent.create().inject(this)

        commonModel.say()
        println("--------------helper:活动：${helper}")
        println("--------------myGson:活动：${myGson}")

    }

    override fun bindListener() {
        findViewById<TextView>(R.id.tv).setOnClickListener {
            //            cameraTask()
            download()
//            testDown()

        }
    }

    private val commonModel by viewModels<CommonViewModel> { App.instance.factory }
    @Inject
    lateinit var myGson: Gson

//    @Inject
//    lateinit var receiver: DownAndInstallReceiver
    @Inject
    lateinit var helper: UpdateHelper


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val a = setContentView<ActivityMainBinding>(this, R.layout.activity_main)
//
//        DaggerActivityComponent.create().inject(this)
//        a.tv.setOnClickListener {
//            cameraTask()
//        }
//    }

    private fun hasCameraPermission(): Boolean {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(
                this,
                "此应用需要使用相机权限",
                Permission.RC_CAMERA_PERM,
                Manifest.permission.CAMERA
            )
        }
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
    }

    @AfterPermissionGranted(Permission.RC_CAMERA_PERM)
    fun cameraTask() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show()
        }
//        else {
//            // Ask for one permission
//            EasyPermissions.requestPermissions(
//                this,
//                "此应用需要使用相机权限",
//                Permission.RC_CAMERA_PERM,
//                Manifest.permission.CAMERA
//            )
//        }
    }


    @AfterPermissionGranted(Permission.RC_STORAGE_PERM)
    fun download() {
        if (hasStoragePermission()) {
            testDown()
        }
    }

    private fun testDown() {
        toast("开始下载")
        val request =
            DownloadManager.Request("https://ebdexstyle.oss-cn-hangzhou.aliyuncs.com/diandian_android_v1.3.3.apk".toUri())
        request.setTitle("通知标题~~~")
        request.setDescription("点点课下载中...")
        request.setVisibleInDownloadsUi(true)

        val file = File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), "点点课安装包.apk")
        if (file.exists()) {
            file.delete()
        }
        request.setDestinationUri(file.toUri())

        manager = App.instance.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        id = manager?.enqueue(request)

        receiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context?, intent: Intent?) {
                checkStatus()
            }
        }
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        println("------------------!@#->${file.absolutePath}")
    }

    private var id: Long? = null
    private var manager: DownloadManager? = null
    private var receiver: BroadcastReceiver? = null

    private fun hasStoragePermission(): Boolean {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            EasyPermissions.requestPermissions(
                this,
                "需要存储卡权限",
                Permission.RC_STORAGE_PERM,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        return EasyPermissions.hasPermissions(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun checkStatus() {
        manager ?: return
        id ?: return
        val query = DownloadManager.Query()
        query.setFilterById(id!!)
        val cursor = manager!!.query(query)
        if (cursor.moveToFirst()) {
            val status = cursor.getInt(cursor.getColumnIndex(DownloadManager.COLUMN_STATUS))
            when (status) {
                DownloadManager.STATUS_PAUSED -> {
                    println("----------!@#->暂停")
                }
                DownloadManager.STATUS_PENDING -> {
                    println("----------!@#->暂停")
                }
                DownloadManager.STATUS_RUNNING -> {
                    println("----------!@#->暂停")
                }
                DownloadManager.STATUS_SUCCESSFUL -> {
                    println("----------!@#->成功")
                    cursor.close()
                    unregisterReceiver(receiver)
                }
                DownloadManager.STATUS_FAILED -> {
                    println("----------!@#->失败")
                    cursor.close()
                    unregisterReceiver(receiver)
                }
            }
        }

    }

}
