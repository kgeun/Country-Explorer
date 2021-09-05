package com.kgeun.countryexplorer.view.activity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.databinding.ActivityMainBinding
import com.kgeun.countryexplorer.view.CEBaseActivity


class BBMainActivity : CEBaseActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val window: Window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = getColor(R.color.primary_teal_1)
    }
}