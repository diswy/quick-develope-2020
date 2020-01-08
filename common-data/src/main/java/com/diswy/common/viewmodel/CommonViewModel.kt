package com.diswy.common.viewmodel

import androidx.lifecycle.ViewModel
import com.diswy.foundation.tools.UpdateHelper
import com.google.gson.Gson
import javax.inject.Inject

class CommonViewModel @Inject constructor(
    val gson: Gson,
    val helper: UpdateHelper
) : ViewModel() {

    fun say() {
    }
}