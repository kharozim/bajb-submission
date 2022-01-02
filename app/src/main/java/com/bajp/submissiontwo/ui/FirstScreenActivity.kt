package com.bajp.submissiontwo.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bajp.submissiontwo.databinding.ActivityFirstScreenBinding
import com.bajp.submissiontwo.ui.home.HomeActivity
import kotlinx.coroutines.delay

class FirstScreenActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFirstScreenBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        lifecycleScope.launchWhenStarted {
            delay(1000)
            val intent = Intent(this@FirstScreenActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}