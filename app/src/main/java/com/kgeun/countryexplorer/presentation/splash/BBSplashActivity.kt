package com.kgeun.countryexplorer.presentation.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.kgeun.countryexplorer.R
import com.kgeun.countryexplorer.databinding.ActivitySplashBinding
import com.kgeun.countryexplorer.presentation.CEBaseActivity
import com.kgeun.countryexplorer.presentation.CEMainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BBSplashActivity : CEBaseActivity() {

    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launch {
            delay(1000L)
            moveToMain()
        }
    }

    fun moveToMain() {
        finish()
        startActivity(Intent(this, CEMainActivity::class.java))
        overridePendingTransition(R.anim.translate_fade_in, android.R.anim.fade_out)
    }
}