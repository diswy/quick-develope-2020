package com.diswy.quick

import android.Manifest
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.diswy.common.App
import com.diswy.common.viewmodel.CommonViewModel
import com.diswy.foundation.base.Permission
import com.diswy.foundation.base.activity.BaseActivity
import com.google.gson.Gson
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : BaseActivity() {
    override fun getLayoutRes(): Int {
        return R.layout.activity_main
    }

    override fun initialize() {
        DaggerActivityComponent.create().inject(this)
    }

    override fun bindListener() {
        findViewById<TextView>(R.id.tv).setOnClickListener {
            cameraTask()
        }
    }

    private val commonModel by viewModels<CommonViewModel> { App.instance.factory }
    @Inject
    lateinit var myGson: Gson

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
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)){
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
}
