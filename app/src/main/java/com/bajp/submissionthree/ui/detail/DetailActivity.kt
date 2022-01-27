package com.bajp.submissionthree.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissionthree.R
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.databinding.ActivityDetailBinding
import com.bajp.submissionthree.databinding.ItemDialogDetailBinding
import com.bajp.submissionthree.utils.ViewModelFactory
import com.bajp.submissionthree.utils.FormatUtil
import com.bajp.submissionthree.utils.IMAGE_URL
import com.bajp.submissionthree.utils.showToast
import com.bumptech.glide.Glide
import dagger.android.support.DaggerAppCompatActivity
import java.util.*
import javax.inject.Inject

class DetailActivity : DaggerAppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val isMovie by lazy { intent.getBooleanExtra(EXTRA_DETAIL_IS_MOVIE, false) }
    private val detailId by lazy { intent.getIntExtra(EXTRA_DETAIL_ID, 0) }
    private lateinit var viewModel: DetailViewModel
    private lateinit var contentItem: CatalogEntity
    @Inject
    lateinit var factory : ViewModelFactory


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, factory)[DetailViewModel::class.java]

        setObserver()

    }

    private fun setObserver() {
        if (isMovie) {
            viewModel.getDetailMovie(detailId).observe(this, { result ->
                if (result != null) {
                    setView(result)
                } else {
                    showToast(getString(R.string.data_not_found))
                }
            })
        } else {
            viewModel.getDetailTv(detailId).observe(this, { result ->
                if (result != null) {
                    setView(result)
                } else {
                    showToast(getString(R.string.data_not_found))
                }
            })
        }
    }

    private fun setView(result: CatalogEntity) {
        contentItem = result
        val dateRelease = FormatUtil.formatDate(result.releaseDate)
        val imageFav = if (result.isFavorite) R.drawable.ic_fav_fill else R.drawable.ic_fav_outline
        binding.run {
            Glide.with(this@DetailActivity)
                .load(IMAGE_URL + result.imagePoster)
                .placeholder(R.drawable.ic_clock)
                .into(ivPoster)
            tvName.text = result.name
            tvRelease.text = dateRelease
            tvRating.text = getString(
                R.string.rating_value,
                result.rating.toString(),
                result.ratingCount.toString()
            )
            tvDescription.text = result.description
            btnFav.setImageResource(imageFav)

            ivPoster.setOnClickListener {
                showDialogImage(result.imagePoster)
            }
            btnFav.setOnClickListener {
                viewModel.setFav(result)
                viewModel.getDetailMovie(detailId)
            }
        }
    }

    private fun showDialogImage(imagePoster: String) {
        val dialog = Dialog(this, R.style.Base_Theme_AppCompat_Dialog_MinWidth)
        val dialogBinding = ItemDialogDetailBinding.inflate(layoutInflater)
        Glide.with(this).load(IMAGE_URL + imagePoster).placeholder(R.mipmap.ic_launcher)
            .into(dialogBinding.ivDialogPoster)
        dialog.setContentView(dialogBinding.root)
        dialog.show()
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.share, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.ivShare) {
            doShare(contentItem)
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun doShare(result: CatalogEntity) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder(this)
            .setType(mimeType)
            .setChooserTitle(getString(R.string.share_now))
            .setText(resources.getString(R.string.share_text, result.name, result.releaseDate))
            .startChooser()

    }


    companion object {
        const val EXTRA_DETAIL_IS_MOVIE = "EXTRA_DETAIL_IS_MOVIE"
        const val EXTRA_DETAIL_ID = "EXTRA_DETAIL_ID"
    }
}