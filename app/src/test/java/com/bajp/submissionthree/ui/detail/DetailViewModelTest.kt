package com.bajp.submissionthree.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.data.source.IRepository
import com.bajp.submissionthree.data.source.Repository
import com.bajp.submissionthree.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel

    private val position = 2

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<CatalogEntity>

    @Before
    fun setup() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun getDetailMovie() {

        val dummyListMovie = DataDummy.generateDataMovie()
        val dummyDetailMovie = dummyListMovie[position]
        val movieId = dummyListMovie[position].id
        val detailMovie = MutableLiveData<CatalogEntity>()
        detailMovie.value = dummyDetailMovie

        `when`(repository.getDetailMovie(movieId)).thenReturn(detailMovie)
        val response = viewModel.getDetailMovie(movieId)
        verify<IRepository>(repository).getDetailMovie(movieId)
        assertNotNull(response)
        assertEquals(detailMovie.value, response.value)

        viewModel.getDetailMovie(movieId).observeForever(observer)
        verify(observer).onChanged(dummyDetailMovie)

    }

    @Test
    fun getDetailTvShow() {

        val dummyListTvShow = DataDummy.generateDataTV()
        val dummyDetailTvShow = dummyListTvShow[position]
        val tvShowId = dummyListTvShow[position].id
        val detailTvShow = MutableLiveData<CatalogEntity>()
        detailTvShow.value = dummyDetailTvShow

        `when`(repository.getDetailTv(tvShowId)).thenReturn(detailTvShow)
        val response = viewModel.getDetailTv(tvShowId)
        verify<IRepository>(repository).getDetailTv(tvShowId)
        assertNotNull(response)
        assertEquals(detailTvShow.value, response.value)

        viewModel.getDetailTv(tvShowId).observeForever(observer)
        verify(observer).onChanged(dummyDetailTvShow)

    }
}

