package com.bajp.submissionone.data.repository

import com.bajp.submissionone.data.ContentEntity
import com.bajp.submissionone.data.entities.ContentItemEntity
import com.bajp.submissionone.utils.DataUtil

class Repository : IRepository {

    override fun getDataMovie(): ContentEntity? {
        val response: ContentEntity? = DataUtil.generateDataMovie()
        response?.results?.forEach {
            it.imagePoster = IMAGE_URL + it.imagePoster
            it.imageSlider = IMAGE_URL + it.imageSlider
        }
        return response
    }

    override fun getDataTv(): ContentEntity? {
        val response: ContentEntity? = DataUtil.generateDataTV()
        response?.results?.forEach {
            it.imagePoster = IMAGE_URL + it.imagePoster
            it.imageSlider = IMAGE_URL + it.imageSlider
        }
        return response
    }

    override fun getDetailMovie(id: Int): ContentItemEntity? {
        val response: ContentEntity = DataUtil.generateDataMovie()
        val data = response.results.find { it.id == id }
        data?.imagePoster = IMAGE_URL + data?.imagePoster
        data?.imageSlider = IMAGE_URL + data?.imageSlider
        return data
    }

    override fun getDetailTv(id: Int): ContentItemEntity? {
        val response = DataUtil.generateDataTV()
        val data = response.results.find { it.id == id }
        data?.imagePoster = IMAGE_URL + data?.imagePoster
        data?.imageSlider = IMAGE_URL + data?.imageSlider
        return data
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}