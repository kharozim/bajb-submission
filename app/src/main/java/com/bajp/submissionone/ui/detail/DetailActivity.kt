package com.bajp.submissionone.ui.detail

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bajp.submissionone.R
import com.bajp.submissionone.data.entities.ContentItemEntity
import com.bajp.submissionone.data.repository.Repository
import com.bajp.submissionone.databinding.ActivityDetailBinding
import com.bajp.submissionone.databinding.ItemDialogDetailBinding
import com.bajp.submissionone.utils.FormatUtil
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val isMovie by lazy { intent.getBooleanExtra(EXTRA_DETAIL_IS_MOVIE, false) }
    private val detailId by lazy { intent.getIntExtra(EXTRA_DETAIL_ID, 0) }
    private lateinit var viewModel: DetailViewModel
    private lateinit var contentItem: ContentItemEntity


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return DetailViewModel(Repository()) as T
            }
        })[DetailViewModel::class.java]

        getData()
        setObserver()

    }

    private fun getData() {
        if (isMovie) {
            viewModel.getDetailMovie(detailId)
        } else {
            viewModel.getDetailTv(detailId)
        }
    }

    private fun setObserver() {
        if (isMovie) {
            viewModel.detailMovie.observe(this, { result ->
                if (result != null) {
                    setView(result)
                } else {
                    showMessage(getString(R.string.data_not_found))
                }
            })
        } else {
            viewModel.detailTv.observe(this, { result ->
                if (result != null) {
                    setView(result)
                } else {
                    showMessage(getString(R.string.data_not_found))
                }
            })
        }
    }

    private fun setView(result: ContentItemEntity) {
        contentItem = result
        val dateRelease = FormatUtil.formatDate(result.releaseDate)
        binding.run {
            Glide.with(this@DetailActivity)
                .load(result.imagePoster)
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
            ivPoster.setOnClickListener {
                showDialogImage(result.imagePoster)
            }
        }
    }

    private fun showDialogImage(imagePoster: String) {
        val dialog = Dialog(this, R.style.Base_Theme_AppCompat_Dialog_MinWidth)
        val dialogBinding = ItemDialogDetailBinding.inflate(layoutInflater)
        Glide.with(this).load(imagePoster).placeholder(R.mipmap.ic_launcher)
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

    private fun doShare(result: ContentItemEntity) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder(this)
            .setType(mimeType)
            .setChooserTitle(getString(R.string.share_now))
            .setText(resources.getString(R.string.share_text, result.name, result.releaseDate))
            .startChooser()

    }


    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }


    companion object {
        const val EXTRA_DETAIL_IS_MOVIE = "EXTRA_DETAIL_IS_MOVIE"
        const val EXTRA_DETAIL_ID = "EXTRA_DETAIL_ID"
    }
}