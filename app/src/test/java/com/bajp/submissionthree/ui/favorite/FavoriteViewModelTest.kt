package com.bajp.submissionthree.ui.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.bajp.submissionthree.data.source.Repository
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.utils.DataDummy
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FavoriteViewModelTest : TestCase() {

    private lateinit var viewModel: FavoriteViewModel
    private val dummyMovie = DataDummy.generateDataMovie()

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var repository: Repository

    @Mock
    private lateinit var observer: Observer<List<CatalogEntity>>

    @Before
    fun setup() {
        viewModel = FavoriteViewModel(repository)
    }

    @Test
    fun testGetFavorite() {
        val listData = MutableLiveData<List<CatalogEntity>>()
        listData.value = dummyMovie

        Mockito.`when`(repository.getFavorite()).thenReturn(listData)
        val favEntity = viewModel.getFavorite().value
        Mockito.verify(repository).getFavorite()
        Assert.assertNotNull(favEntity)
        Assert.assertEquals(dummyMovie.size,favEntity?.size)

        viewModel.getFavorite().observeForever(observer)
        verify(observer).onChanged(listData.value)
    }
}