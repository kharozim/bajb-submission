package com.bajp.submissionthree.ui.favorite

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissionthree.R
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.databinding.ActivityFavoriteBinding
import com.bajp.submissionthree.ui.detail.DetailActivity
import com.bajp.submissionthree.utils.ViewModelFactory
import com.bajp.submissionthree.ui.home.ItemClick
import com.bajp.submissionthree.utils.showToast
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class FavoriteActivity : DaggerAppCompatActivity() {

    private val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private lateinit var viewModel: FavoriteViewModel
    @Inject
    lateinit var factory : ViewModelFactory
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]
        setObserver()
    }

    private fun setObserver() {
        viewModel.getFavorite().observe(this, { result ->
            if (result.isNotEmpty()) {
                showData(result)
            } else {
                showToast(getString(R.string.data_not_found))
            }
        })
    }

    private fun showData(result: List<CatalogEntity>?) {
        val adapter = FavoriteAdapter()
        adapter.onClickItem(object : ItemClick {
            override fun onItemClick(data: Any?, position: Int) {
                data as CatalogEntity
                val intent = Intent(this@FavoriteActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_IS_MOVIE, data.isMovie)
                intent.putExtra(DetailActivity.EXTRA_DETAIL_ID, data.id)
                startActivity(intent)
            }

        })
        adapter.setData(result ?: emptyList())
        binding.rvFavorite.adapter = adapter

    }
}