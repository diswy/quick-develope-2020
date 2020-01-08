package com.diswy.common.viewmodel

import androidx.lifecycle.ViewModel
import com.diswy.foundation.receiver.DownAndInstallReceiver
import com.diswy.foundation.tools.UpdateHelper
import com.google.gson.Gson
import javax.inject.Inject

class CommonViewModel @Inject constructor(
    val gson: Gson,
//    val receiver:DownAndInstallReceiver,
    val helper:UpdateHelper
) : ViewModel() {

    fun say(){
//        println("--------------receiver:${receiver}")
        println("--------------helper:${helper}")
        println("--------------gson:${gson}")
    }
}