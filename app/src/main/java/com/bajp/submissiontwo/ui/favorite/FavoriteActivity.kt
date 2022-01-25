package com.bajp.submissiontwo.ui.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissiontwo.R
import com.bajp.submissiontwo.databinding.ActivityFavoriteBinding
import com.bajp.submissiontwo.ui.ViewModelFactory
import com.bajp.submissiontwo.utils.showToast

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
                showData()
            } else {
                showToast(getString(R.string.data_not_found))
            }
        })
    }

    private fun showData() {
        val adapter = FavoriteAdapter()
    }
}