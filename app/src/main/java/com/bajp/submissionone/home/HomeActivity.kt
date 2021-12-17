package com.bajp.submissionone.home

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.bajp.submissionone.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setupSlider()
        setupMainContent()

    }
    private fun setupSlider() {

    }

    private fun setupMainContent() {
        val adapter = HomeViewPager(supportFragmentManager, lifecycle = lifecycle)
    }



}