package com.kgeun.countryexplorer.view

import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import com.kgeun.countryexplorer.utils.CEUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
open class CEBaseActivity : AppCompatActivity() {

    var errorLiveData = MutableLiveData<(String?) -> Unit> {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOrientation()
        registerErrorHandler()
    }

    private fun registerErrorHandler() {
        errorLiveData.postValue (CEUtils.errorHandler(this))
    }

    private fun setOrientation() {
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
    }
}