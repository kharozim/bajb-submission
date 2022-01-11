package com.bajp.submissiontwo.ui.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.data.source.IRepository
import com.bajp.submissiontwo.data.source.Repository
import com.bajp.submissiontwo.utils.DataEntityUtil
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
    private lateinit var observer: Observer<ContentItemEntity>

    @Before
    fun setup() {
        viewModel = DetailViewModel(repository)
    }

    @Test
    fun getDetailMovie() {

        val dummyListMovie = DataEntityUtil.generateDataMovie()
        val dummydetailMovie = dummyListMovie.results[position]
        val movieId = dummyListMovie.results[position].id
        val detailMovie = MutableLiveData<ContentItemEntity>()
        detailMovie.value = dummydetailMovie

        `when`(repository.getDetailMovie(movieId)).thenReturn(detailMovie)
        val response = viewModel.getDetailMovie(movieId)
        verify<IRepository>(repository).getDetailMovie(movieId)
        assertNotNull(response)
        assertEquals(detailMovie.value, response.value)

        viewModel.getDetailMovie(movieId).observeForever(observer)
        verify(observer).onChanged(dummydetailMovie)

    }

    @Test
    fun getDetailTvShow() {

        val dummyListTvShow = DataEntityUtil.generateDataTV()
        val dummyDetailTvShow = dummyListTvShow.results[position]
        val tvShowId = dummyListTvShow.results[position].id
        val detailTvShow = MutableLiveData<ContentItemEntity>()
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

