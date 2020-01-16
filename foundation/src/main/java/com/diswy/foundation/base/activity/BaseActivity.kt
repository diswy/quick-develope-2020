package com.diswy.foundation.base.activity

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.diswy.foundation.R
import com.diswy.foundation.base.Permission
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

abstract class BaseActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    /**
     * 保持屏幕常亮
     */
    protected open fun keepScreenOn() = false

    /**
     * 视图无默认颜色，可以减少视图层级。
     * 在主题中设置默认颜色，通常不必使用该方法
     */
    protected open fun emptyBackground() = false

    /**
     * 标题栏颜色模式
     */
    protected open fun statusDarkMode() = false

    /**
     * 全屏模式，该模式下看不到时间、电池等图标。沉浸式体验
     */
    protected open fun fullScreenMode() = false

    /**
     * 透明状态栏样式
     */
    protected open fun translucentMode() = false

    /**
     * 获取视图ID
     */
    abstract fun getLayoutRes(): Int

    protected open fun setView() {
        setContentView(getLayoutRes())
    }

    abstract fun initialize()

    protected open fun bindListener() {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (emptyBackground()) {
            window.decorView.background = null
        }
        if (keepScreenOn()) {
            window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        if (statusDarkMode()) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (fullScreenMode()) {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        } else if (translucentMode()) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window.statusBarColor = Color.TRANSPARENT
        }

        setView()
        initialize()
        bindListener()
    }

    /****************权限管理****************/
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

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    protected fun hasCameraPermission(): Boolean {
        if (!EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.request_camera_permission),
                Permission.RC_CAMERA_PERM,
                Manifest.permission.CAMERA
            )
        }
        return EasyPermissions.hasPermissions(this, Manifest.permission.CAMERA)
    }

    protected fun hasStoragePermission(): Boolean {
        if (!EasyPermissions.hasPermissions(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        ) {
            EasyPermissions.requestPermissions(
                this,
                getString(R.string.request_storage_permission),
                Permission.RC_STORAGE_PERM,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
        return EasyPermissions.hasPermissions(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
    }
    /****************权限管理****************/

}