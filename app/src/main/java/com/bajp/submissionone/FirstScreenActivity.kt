package com.bajp.submissionone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.bajp.submissionone.databinding.ActivityFirstScreenBinding
import com.bajp.submissionone.ui.home.HomeActivity
import kotlinx.coroutines.delay

class FirstScreenActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFirstScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(binding.root)
        lifecycleScope.launchWhenStarted {
            delay(1000)
            val intent = Intent(this@FirstScreenActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}