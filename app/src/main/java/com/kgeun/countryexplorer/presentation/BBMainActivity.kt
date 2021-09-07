package com.kgeun.countryexplorer.presentation

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.databinding.ActivityMainBinding


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