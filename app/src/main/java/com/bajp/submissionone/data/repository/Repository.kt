package com.bajp.submissionone.data.repository

import com.bajp.submissionone.data.ContentEntity
import com.google.gson.Gson

class Repository : IRepository {

    override fun getDataMovie(dataResource: String): ContentEntity {
        val data = Gson().fromJson(dataResource, ContentEntity::class.java)
        data.results.forEach {
            it.imagePoster = IMAGE_URL + it.imagePoster
            it.imageSlider = IMAGE_URL + it.imageSlider
        }
        return data
    }

    override fun getDataTv(dataResource: String): ContentEntity {
        val data = Gson().fromJson(dataResource, ContentEntity::class.java)
        data.results.forEach {
            it.imagePoster = IMAGE_URL + it.imagePoster
            it.imageSlider = IMAGE_URL + it.imageSlider
        }
        return data
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w300"
    }
}