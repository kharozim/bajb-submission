package com.bajp.submissionthree.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissionthree.R
import com.bajp.submissionthree.data.source.local.entities.ContentItemEntity
import com.bajp.submissionthree.databinding.ActivityFavoriteBinding
import com.bajp.submissionthree.ui.ViewModelFactory
import com.bajp.submissionthree.ui.home.ItemClick
import com.bajp.submissionthree.utils.showToast

class FavoriteActivity : AppCompatActivity() {

    private val binding by lazy { ActivityFavoriteBinding.inflate(layoutInflater) }
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val factory = ViewModelFactory.getInstance(this)
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

    private fun showData(result: List<ContentItemEntity>?) {
        val adapter = FavoriteAdapter()
        adapter.onClickItem(object : ItemClick {
            override fun onItemClick(data: Any?, position: Int) {
                data as ContentItemEntity
                showToast("$position : ${data.name}")
            }

        })
        adapter.setData(result ?: emptyList())
        binding.rvFavorite.adapter = adapter

    }
}