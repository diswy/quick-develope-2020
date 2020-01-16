package com.diswy.quick

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
import timber.log.Timber
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

        Timber.d("debug")
        Timber.i("info")
        Timber.w("warn")
        Timber.e("error")
    }

    override fun bindListener() {
        findViewById<TextView>(R.id.tv).setOnClickListener {
            download()
        }
    }


    @AfterPermissionGranted(Permission.RC_CAMERA_PERM)
    fun cameraTask() {
        if (hasCameraPermission()) {
            Toast.makeText(this, "TODO: Camera things", Toast.LENGTH_LONG).show()
        }
    }

    //    @AfterPermissionGranted(Permission.RC_STORAGE_PERM)
    fun download() {
        helper.download(this)
    }


}
