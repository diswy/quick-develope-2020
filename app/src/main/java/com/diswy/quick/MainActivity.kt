package com.diswy.quick

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil.setContentView
import com.diswy.common.App
import com.diswy.common.viewmodel.CommonViewModel
import com.diswy.quick.databinding.ActivityMainBinding
import com.google.gson.Gson
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val commonModel by viewModels<CommonViewModel> { App.instance.factory }
    @Inject
    lateinit var myGson: Gson

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        DaggerActivityComponent.create().inject(this)
    }
}
