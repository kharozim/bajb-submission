package com.bajp.submissionone.ui.detail

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ShareCompat
import com.bajp.submissionone.R
import com.bajp.submissionone.data.entities.ContentItemEntity
import com.bajp.submissionone.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DetailActivity : AppCompatActivity() {

    private val binding by lazy { ActivityDetailBinding.inflate(layoutInflater) }
    private val isMovie by lazy { intent.getBooleanExtra(EXTRA_DETAIL_IS_MOVIE, false) }
    private val detailId by lazy { intent.getIntExtra(EXTRA_DETAIL_ID, 0) }
    private val viewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

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
                    showMessage("Data Not Found")
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

        val dateRelease = formatDate(result.releaseDate)
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
            ivShare.setOnClickListener {
                doShare(result)
            }
        }
    }

    private fun doShare(result: ContentItemEntity) {
        val mimeType = "text/plain"
        ShareCompat.IntentBuilder(this)
            .setType(mimeType)
            .setChooserTitle("Bagikan aplikasi ini sekarang")
            .setText(resources.getString(R.string.share_text, result.name, result.releaseDate))
            .startChooser()

    }

    @SuppressLint("SimpleDateFormat")
    private fun formatDate(dateString: String): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        val newDate =
            try {
                dateFormat.parse(dateString)
            } catch (e: ParseException) {
                null
            }
        val newFormat = SimpleDateFormat("MMM dd, yyyy")
        val newDateFormat = newDate?.let { newFormat.format(it) }
        return newDateFormat ?: ""
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_DETAIL_IS_MOVIE = "EXTRA_DETAIL_IS_MOVIE"
        const val EXTRA_DETAIL_ID = "EXTRA_DETAIL_ID"
    }
}