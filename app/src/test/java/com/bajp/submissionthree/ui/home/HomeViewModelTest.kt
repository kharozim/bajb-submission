package com.bajp.submissionthree.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.PagedList
import com.bajp.submissionthree.data.source.IRepository
import com.bajp.submissionthree.data.source.Repository
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.vo.Resource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : TestCase() {

    private lateinit var viewModel: HomeViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<Resource<PagedList<CatalogEntity>>>

    @Mock
    private lateinit var pagedList: PagedList<CatalogEntity>


    @Before
    fun setup() {
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun getListMovie() {
        val dummyData = Resource.success(pagedList)
        `when`(dummyData.data?.size).thenReturn(5)
        val movie = MutableLiveData<Resource<PagedList<CatalogEntity>>>()
        movie.value = dummyData

        `when`(repository.getDataMovie()).thenReturn(movie)
        val movieEntity = viewModel.getListMovie().value?.data

        verify<IRepository>(repository).getDataMovie()

        assertNotNull(movieEntity)
        assertEquals(5, movieEntity?.size)

        viewModel.getListMovie().observeForever(observer)
        verify(observer).onChanged(dummyData)
    }

    @Test
    fun testGetListTV() {
        val dummyData = Resource.success(pagedList)
        `when`(dummyData.data?.size).thenReturn(5)
        val movie = MutableLiveData<Resource<PagedList<CatalogEntity>>>()
        movie.value = dummyData

        `when`(repository.getDataTv()).thenReturn(movie)
        val tvEntity = viewModel.getListTV().value?.data

        verify<IRepository>(repository).getDataTv()

        assertNotNull(tvEntity)
        assertEquals(5, tvEntity?.size)

        viewModel.getListTV().observeForever(observer)
        verify(observer).onChanged(dummyData)
    }
}