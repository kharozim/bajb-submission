package com.bajp.submissionthree.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bajp.submissionthree.R
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.databinding.ActivityHomeBinding
import com.bajp.submissionthree.ui.detail.DetailActivity
import com.bajp.submissionthree.ui.favorite.FavoriteActivity
import com.bajp.submissionthree.utils.ViewModelFactory
import com.bajp.submissionthree.vo.Status
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeActivity : DaggerAppCompatActivity() {

    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var factory: ViewModelFactory

    private var isMovie = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        homeViewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]
        setupMainContent()
        setupSlider()
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
                } else {
                    tab.text = getString(R.string.tv_show)
                }
            }
                .attach()

            tabLayoutMain.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    isMovie = tab?.position == 0
                    setupSlider()
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }

            })
        }
    }

    private fun setupSlider() {
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
                        it.data?.let { data -> setupViewPagerSlider(data) }
                        showLoading(false)
                    }
                }
            })
        } else {
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
                        it.data?.let { data -> setupViewPagerSlider(data) }
                        showLoading(false)
                    }
                }
            })
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressHome.isVisible = isLoading
        binding.viewPagerSlider.visibility = if (isLoading) View.INVISIBLE else View.VISIBLE
    }

    private fun setupViewPagerSlider(results: List<CatalogEntity>) {
        val adapter = HomeViewPagerSlider(this, results)
        adapter.notifyItemRangeChanged(0, adapter.itemCount)
        adapter.onItemClick(object : ItemClick {
            override fun onItemClick(data: Any?, position: Int) {
                data as CatalogEntity
                val intent = Intent(this@HomeActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_IS_MOVIE, data.isMovie)
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.favorite, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.ivMenuFavorite) {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
            true
        } else {
            super.onOptionsItemSelected(item)
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