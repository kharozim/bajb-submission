package com.bajp.submissiontwo.ui.home

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.espresso.IdlingRegistry
import com.bajp.submissiontwo.data.entities.ContentEntity
import com.bajp.submissiontwo.data.repository.IRepository
import com.bajp.submissiontwo.data.repository.Repository
import com.bajp.submissiontwo.utils.DataUtil
import com.bajp.submissiontwo.utils.DataUtil2
import com.bajp.submissiontwo.utils.EspressoIdlingResource
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest : TestCase() {

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: IRepository

    @Mock
    private lateinit var observer: Observer<ContentEntity>

    @Before
    fun setup() {
        repository = mock(Repository::class.java)
        homeViewModel = HomeViewModel(repository)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tutup() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun getListMovie() {
//        var listData = MutableLiveData<ContentEntity>()
        `when`(repository.getDataMovie()).thenReturn(DataUtil2.generateDataMovie() as LiveData<ContentEntity>)
        val listMovie = homeViewModel.getListMovie()
        verify<IRepository>(repository).getDataMovie()
        assertNotNull(listMovie)
//        assertNotNull(listMovie?.results?.size, mockData.value?.results?.size)

//        homeViewModel.getListMovie().observeForever(observer)
//        verify(observer).onChanged(mockData.value)

    }

//    @Before
//    fun before() {
//        contentEntity = mock(ContentEntity::class.java)
//        repository = Repository()
//        homeViewModel = HomeViewModel(repository)
//    }
//
//    @Test
//    fun testGetListMovie() {
//        repository = Repository()
//        homeViewModel = HomeViewModel(repository)
//        val dummyMovie = DataUtil.generateDataMovie()
//        dummyMovie.results.forEach {
//            it.imagePoster = IMAGE_URL + it.imagePoster
//            it.imageSlider = IMAGE_URL + it.imageSlider
//        }
//        contentEntity = repository.getDataMovie() ?: ContentEntity()
//        assertNotNull(contentEntity)
//        assertEquals(
//            dummyMovie.results.size,
//            contentEntity?.results?.size ?: 0
//        )
//    }
//
//    @Test
//    fun testGetListTV() {
//        repository = Repository()
//        homeViewModel = HomeViewModel(repository)
//        val dummyTv = DataUtil.generateDataTV()
//        dummyTv.results.forEach {
//            it.imagePoster = IMAGE_URL + it.imagePoster
//            it.imageSlider = IMAGE_URL + it.imageSlider
//        }
//        contentEntity = repository.getDataTv() ?: ContentEntity()
//        assertNotNull(contentEntity)
//        assertEquals(dummyTv.results.size, contentEntity?.results?.size ?: 0)
//    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}