package com.diswy.quick

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil.setContentView
import com.diswy.common.App
import com.diswy.common.viewmodel.CommonViewModel
import com.diswy.quick.databinding.ActivityMainBinding
import com.google.gson.Gson
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

class MainActivity : AppCompatActivity(),EasyPermissions.PermissionCallbacks {

    private val commonModel by viewModels<CommonViewModel> { App.instance.factory }
    @Inject
    lateinit var myGson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val a = setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        DaggerActivityComponent.create().inject(this)
        a.tv.setOnClickListener {
            cameraTask()
        }
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
    }

    @AfterPermissionGranted(123)
    fun cameraTask() {
        if (hasCameraPermission()) {
            // Have permission, do the thing!
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                "文字描述",
                123,
                Manifest.permission.CAMERA
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build().show()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {

    }
}
