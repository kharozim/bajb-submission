package com.bajp.submissiontwo.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bajp.submissiontwo.R
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.databinding.ActivityHomeBinding
import com.bajp.submissiontwo.ui.ViewModelFactory
import com.bajp.submissiontwo.ui.detail.DetailActivity
import com.bajp.submissiontwo.vo.Status
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private lateinit var homeViewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val factory = ViewModelFactory.getInstance(this)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        setupMainContent()
    }

    private fun setupMainContent() {
        val adapter = HomeViewPager(supportFragmentManager, lifecycle = lifecycle)
        with(binding.viewPagerContent) {
            isUserInputEnabled = false
            this.adapter = adapter
        }
        binding.run {
            TabLayoutMediator(tabLayoutMain, viewPagerContent)
            { tab, position ->
                if (position == 0) {
                    tab.text = getString(R.string.movie)
                    setupSlider(isMovie = true)
                } else {
                    tab.text = getString(R.string.tv_show)
                    setupSlider(isMovie = false)
                }
            }
                .attach()

            tabLayoutMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    if (tab?.position == 0) {
                        setupSlider(isMovie = true)
                    } else {
                        setupSlider(isMovie = false)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }
    }

    private fun setupSlider(isMovie: Boolean) {
        if (isMovie) {
            homeViewModel.getListMovie().observe(this, {
                when (it.status) {
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        showToast("error")
                    }
                    Status.SUCCESS -> {
                        it.data?.let {data-> setupViewPagerSlider(data, isMovie) }
                        showLoading(false)
                        Log.e("TAG", "setupTv: movi \n" + Gson().toJson(it.data))
                    }
                }

            })
        } else {
            Log.e("TAG", "setupTv: tv")
            homeViewModel.getListTV().observe(this, {
                when (it.status) {
                    Status.LOADING -> {
                        showLoading(true)
                    }
                    Status.ERROR -> {
                        showLoading(false)
                        showToast("error")

                    }
                    Status.SUCCESS -> {
                        it.data?.let {data-> setupViewPagerSlider(data, isMovie) }
                        showLoading(false)
                    }
                }
            })
        }
    }

    private fun showToast(message : String){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressHome.isVisible = isLoading
        binding.viewPagerSlider.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun setupViewPagerSlider(results: List<ContentItemEntity>, isMovie: Boolean) {
        val adapter = HomeViewPagerSlider(this, results)
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
        adapter.onItemClick(object : ItemClick {
            override fun onItemClick(data: Any?, position: Int) {
                data as ContentItemEntity
                val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_IS_MOVIE, isMovie)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_ID, data.id)
                startActivity(intent)
            }
        })
        with(binding.viewPagerSlider) {
            this.adapter = adapter
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val slideTransformer = CompositePageTransformer()
        slideTransformer.addTransformer(MarginPageTransformer(50))
        slideTransformer.addTransformer { page, position ->
            val distance = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + distance * 0.15f
        }

        binding.viewPagerSlider.setPageTransformer(slideTransformer)
        binding.viewPagerSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable, sliderTime)
            }
        })
    }

    private val sliderTime = 3000L
    private val sliderHandler = Handler(Looper.getMainLooper())
    private val sliderRunnable = Runnable {
        binding.run {
            viewPagerSlider.currentItem++
            if (viewPagerSlider.currentItem == (viewPagerSlider.adapter?.itemCount ?: 0) - 1) {
                lifecycleScope.launch {
                    delay(sliderTime)
                    viewPagerSlider.currentItem = -1
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        sliderHandler.postDelayed(sliderRunnable, sliderTime)
    }

    override fun onPause() {
        super.onPause()
        sliderHandler.removeCallbacks(sliderRunnable)
    }

    override fun onDestroy() {
        super.onDestroy()
        sliderHandler.removeCallbacks(sliderRunnable)
    }
}