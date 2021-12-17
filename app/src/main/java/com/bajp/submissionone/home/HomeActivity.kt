package com.bajp.submissionone.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bajp.submissionone.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

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
        with(binding.viewPagerSlider) {
            isUserInputEnabled = false
            this.adapter = adapter
        }
        binding.run {
            TabLayoutMediator(tabLayoutMain, viewPagerSlider) { tab, position ->
                tab.text = if (position == 0) {
                    "Movie"
                } else {
                    "Tv Show"
                }
            }
                .attach()
        }
    }


}