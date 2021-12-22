package com.bajp.submissionone.ui.detail

import com.bajp.submissionone.data.entities.ContentItemEntity
import com.bajp.submissionone.data.repository.Repository
import com.bajp.submissionone.utils.DataUtil
import com.google.gson.Gson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class DetailViewModelTest {

    private lateinit var repository: Repository
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var dataDetail: ContentItemEntity
    private val dataDummy = DataUtil

    @Before
    fun setup() {
        dataDetail = mock(ContentItemEntity::class.java)
        repository = mock(Repository::class.java)
        detailViewModel = DetailViewModel(repository)
    }

    @Test
    fun testGetDetailMovie() {
        val id = 566525
        repository = Repository()
        detailViewModel = DetailViewModel(repository)
        val response = repository.getDetailMovie(id)
        val listDummy = dataDummy.generateDataMovie().results
        val detailDummy = listDummy.find { it.id == id }
        detailDummy?.imagePoster = IMAGE_URL + detailDummy?.imagePoster
        detailDummy?.imageSlider = IMAGE_URL + detailDummy?.imageSlider
        assertNotNull(detailDummy)
        assertEquals(Gson().toJson(detailDummy), Gson().toJson(response))
    }

    @Test
    fun testGetDetailTv() {
        val id = 2778
        repository = Repository()
        detailViewModel = DetailViewModel(repository)
        val response = repository.getDetailTv(id)
        val listDummy = dataDummy.generateDataTV().results
        val detailDummy = listDummy.find { it.id == id }
        detailDummy?.imagePoster = IMAGE_URL + detailDummy?.imagePoster
        detailDummy?.imageSlider = IMAGE_URL + detailDummy?.imageSlider

        assertNotNull(detailDummy)
        assertEquals(Gson().toJson(detailDummy), Gson().toJson(response))
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}

