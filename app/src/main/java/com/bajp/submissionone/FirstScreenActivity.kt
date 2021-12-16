package com.bajp.submissionone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.bajp.submissionone.databinding.ActivityFirstScreenBinding
import kotlinx.coroutines.delay

class FirstScreenActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFirstScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_first_screen)

//        lifecycleScope.launchWhenStarted {
//            delay(300)
//            delay(200)
//        }

    }
}