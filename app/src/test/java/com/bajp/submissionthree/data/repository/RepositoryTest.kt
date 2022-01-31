package com.bajp.submissionthree.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.bajp.submissionthree.data.source.local.LocalDataSource
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.data.source.remote.RemoteResource
import com.bajp.submissionthree.utils.DataDummy
import com.bajp.submissionthree.utils.LiveDataTestUtil
import com.bajp.submissionthree.utils.PagedListUtil
import com.bajp.submissionthree.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class RepositoryTest{

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val listMovie = DataDummy.generateDataMovie()
    private val listTv = DataDummy.generateDataTV()
    private val remote = mock(RemoteResource::class.java)
    private val local = mock(LocalDataSource::class.java)
    private val fakeRepo = FakeRepository(remote, local)

    private val movie = listMovie[0]
    private val tvShow = listTv[0]

    @Test
    fun getListMovie() {
        val dataResourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, CatalogEntity>
        `when`(local.getMovies()).thenReturn(dataResourceFactory)
        fakeRepo.getDataMovie()
        val movieEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataMovie()))
        verify(local).getMovies()
        assertNotNull(movieEntity.data)
        assertEquals(listMovie.size, movieEntity.data?.size)
    }

    @Test
    fun getListTvShow() {
        val dataResourceFactory = mock(DataSource.Factory::class.java) as DataSource.Factory<Int, CatalogEntity>
        `when`(local.getTvs()).thenReturn(dataResourceFactory)
        fakeRepo.getDataTv()

        val tvEntity = Resource.success(PagedListUtil.mockPagedList(DataDummy.generateDataTV()))
        verify(local).getTvs()
        assertNotNull(tvEntity.data)
        assertEquals(listTv.size, tvEntity.data?.size)
    }

    @Test
    fun getDetailMovie() {
        val dummyMovie = MutableLiveData<CatalogEntity>()
        dummyMovie.value = movie
        `when`(local.getDetailMovie(movie.id)).thenReturn(dummyMovie)

        val data = LiveDataTestUtil.getValue(fakeRepo.getDetailMovie(movie.id))
        verify(local).getDetailMovie(movie.id)
        assertNotNull(data)
        assertEquals(movie.id, data.id)
    }

    @Test
    fun getDetailTv() {
        val dummyMovie = MutableLiveData<CatalogEntity>()
        dummyMovie.value = tvShow
        `when`(local.getDetailTv(tvShow.id)).thenReturn(dummyMovie)

        val data = LiveDataTestUtil.getValue(fakeRepo.getDetailTv(tvShow.id))
        verify(local).getDetailTv(tvShow.id)
        assertNotNull(data)
        assertEquals(tvShow.id, data.id)
    }

    @Test
    fun getFavorite() {
        val dummyFav = MutableLiveData<List<CatalogEntity>>()
        dummyFav.value = listMovie
        `when`(local.getFavorite()).thenReturn(dummyFav)

        val data = LiveDataTestUtil.getValue(fakeRepo.getFavorite())
        verify(local).getFavorite()
        assertNotNull(data)
        assertEquals(listMovie.size, data.size)
    }

}