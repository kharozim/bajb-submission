package com.bajp.submissiontwo.ui.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bajp.submissiontwo.data.entities.ContentEntity
import com.bajp.submissiontwo.data.repository.IRepository
import com.bajp.submissiontwo.data.repository.Repository
import com.bajp.submissiontwo.utils.DataUtil2
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
    private lateinit var observer: Observer<ContentEntity>

    @Before
    fun setup() {
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun getListMovie() {
        val dummyData = DataUtil2.generateDataMovie()
        val listData = MutableLiveData<ContentEntity>()
        listData.value = dummyData
        `when`(repository.getDataMovie()).thenReturn(listData)
        val listMovie = viewModel.getListMovie().value
        verify<IRepository>(repository).getDataMovie()
        assertNotNull(listMovie)
        assertEquals(listData.value?.results, listMovie?.results)

        viewModel.getListMovie().observeForever(observer)
        verify(observer).onChanged(dummyData)
    }

    @Test
    fun testGetListTV() {
        val dummyTv = DataUtil2.generateDataTV()
        val listData = MutableLiveData<ContentEntity>()
        listData.value = dummyTv
        `when`(repository.getDataTv()).thenReturn(listData)
        val listTvShow = viewModel.getListTV()
        verify<IRepository>(repository).getDataTv()
        assertNotNull(listTvShow)
        assertEquals(listData, listTvShow)

        viewModel.getListTV().observeForever(observer)
        verify(observer).onChanged(dummyTv)
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}