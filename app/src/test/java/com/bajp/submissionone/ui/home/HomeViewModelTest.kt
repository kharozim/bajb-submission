package com.bajp.submissionone.ui.home

import com.bajp.submissionone.data.entities.ContentEntity
import com.bajp.submissionone.data.repository.IRepository
import com.bajp.submissionone.data.repository.Repository
import com.bajp.submissionone.utils.DataUtil
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock

class HomeViewModelTest : TestCase() {

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var repository: IRepository
    private var contentEntity: ContentEntity? = null

    @Before
    fun before() {
        contentEntity = mock(ContentEntity::class.java)
        repository = Repository()
        homeViewModel = HomeViewModel(repository)
    }

    @Test
    fun testGetListMovie() {
        repository = Repository()
        homeViewModel = HomeViewModel(repository)
        val dummyMovie = DataUtil.generateDataMovie()
        dummyMovie.results.forEach {
            it.imagePoster = IMAGE_URL + it.imagePoster
            it.imageSlider = IMAGE_URL + it.imageSlider
        }
        contentEntity = repository.getDataMovie() ?: ContentEntity()
        assertNotNull(contentEntity)
        assertEquals(
            dummyMovie.results.size,
            contentEntity?.results?.size ?: 0
        )
    }

    @Test
    fun testGetListTV() {
        repository = Repository()
        homeViewModel = HomeViewModel(repository)
        val dummyTv = DataUtil.generateDataTV()
        dummyTv.results.forEach {
            it.imagePoster = IMAGE_URL + it.imagePoster
            it.imageSlider = IMAGE_URL + it.imageSlider
        }
        contentEntity = repository.getDataTv() ?: ContentEntity()
        assertNotNull(contentEntity)
        assertEquals(dummyTv.results.size, contentEntity?.results?.size ?: 0)
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}