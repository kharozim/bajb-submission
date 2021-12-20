package com.bajp.submissionone.data.repository

import com.bajp.submissionone.utils.DataUtil
import com.google.gson.Gson
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test

class RepositoryTest : TestCase() {

    private lateinit var repository: IRepository
    private val dummyData = DataUtil

    @Before
    fun before() {
        repository = Repository()
    }

    @Test
    fun testGetDataMovie() {
        repository = Repository()
        val response = repository.getDataMovie()
        val dummyMovie = dummyData.generateDataMovie()
        dummyMovie.results.forEach {
            it.imagePoster = IMAGE_URL + it.imagePoster
            it.imageSlider = IMAGE_URL + it.imageSlider
        }
        assertEquals(Gson().toJson(response), Gson().toJson(dummyMovie))
    }

    @Test
    fun testGetDataTv() {
        repository = Repository()
        val response = repository.getDataTv()
        val dummyTv = dummyData.generateDataTV()
        dummyTv.results.forEach {
            it.imagePoster = IMAGE_URL + it.imagePoster
            it.imageSlider = IMAGE_URL + it.imageSlider
        }
        assertEquals(Gson().toJson(dummyTv), Gson().toJson(response))
    }

    @Test
    fun testGetDetailMovie() {
        repository = Repository()
        val dummyId = 88329
        val response = repository.getDetailMovie(dummyId)
        val dummyMovie = dummyData.generateDataMovie()
        val dummyDetailMovie = dummyMovie.results.find { it.id == dummyId }
        assertEquals(Gson().toJson(dummyDetailMovie), Gson().toJson(response))
    }

    @Test
    fun testGetDetailTv() {
        repository = Repository()
        val dummyId = 429617
        val response = repository.getDetailTv(dummyId)
        val dummyTv = dummyData.generateDataTV()
        val dummyDetailTv = dummyTv.results.find { it.id == dummyId }
        assertEquals(Gson().toJson(dummyDetailTv), Gson().toJson(response))
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}