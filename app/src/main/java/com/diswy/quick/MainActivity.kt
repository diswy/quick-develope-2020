package com.diswy.quick

import android.Manifest
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.diswy.common.App
import com.diswy.common.viewmodel.CommonViewModel
import com.diswy.foundation.base.Permission
import com.diswy.foundation.base.activity.BaseActivity
import com.diswy.foundation.tools.UpdateHelper
import com.google.gson.Gson
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject
    lateinit var myGson: Gson
    @Inject
    lateinit var helper: UpdateHelper

    private val commonModel by viewModels<CommonViewModel> { App.instance.factory }

    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initialize() {
        DaggerActivityComponent.builder().appComponent(App.appComponent).build().inject(this)
        commonModel.say()
    }

    override fun bindListener() {
        findViewById<TextView>(R.id.tv).setOnClickListener {
            download()
        }
    }

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
    }

    @AfterPermissionGranted(Permission.RC_STORAGE_PERM)
    fun download() {
        if (hasStoragePermission()) {
            helper.download(this)
        }
    }

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

}
