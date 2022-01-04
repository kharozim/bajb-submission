package com.bajp.submissiontwo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bajp.submissiontwo.data.repository.remote.RemoteResource
import com.bajp.submissiontwo.utils.DataResponseUtil
import com.bajp.submissiontwo.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest : TestCase() {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val movieResponse = DataResponseUtil.generateDataMovie()
    private val tvResponse = DataResponseUtil.generateDataTV()
    private val remote = mock(RemoteResource::class.java)
    private val fakeRepo = FakeRepository(remote)

    @Test
    fun getListMovie() {
        doAnswer { invocation ->
            (invocation.arguments[0] as RemoteResource.CallbackListMovie)
                .onAllMovieReceived(movieResponse)
            null
        }.`when`(remote).getDataMovie(any())
        val contentEntity = LiveDataTestUtil.getValue(fakeRepo.getDataMovie())
        verify(remote).getDataMovie(any())
        assertNotNull(contentEntity)
        assertEquals(movieResponse.size.toLong(), contentEntity.results.size.toLong())
    }

    @Test
    fun getListTvShow() {
        doAnswer { invoc ->
            (invoc.arguments[0] as RemoteResource.CallBackListTvShow)
                .onAllTvShowReceived(tvResponse)
        }.`when`(remote).getDataTv(any())
        val contentEntity = LiveDataTestUtil.getValue(fakeRepo.getDataTv())
        verify(remote).getDataTv(any())
        assertNotNull(contentEntity)
        assertEquals(tvResponse.size.toLong(), contentEntity.results.size.toLong())
    }

    @Test
    fun getDetailMovie() {
        val position = 5
        val id = movieResponse[position].id ?: 0
        val dummyDetailMovie = movieResponse[position]
        doAnswer { invoc ->
            (invoc.arguments[1] as RemoteResource.CallbackDetailMovie)
                .onDetailMovieReceived(dummyDetailMovie)
        }.`when`(remote).getDetailMovie(eq(id), any())
        val detailMovie = LiveDataTestUtil.getValue(fakeRepo.getDetailMovie(id))
        verify(remote).getDetailMovie(eq(id), any())
        assertNotNull(detailMovie)
        assertEquals(dummyDetailMovie.name, detailMovie.name)
    }

    @Test
    fun getDetailTv() {
        val position = 1
        val id = tvResponse[position].id ?: 0
        val dummyDetailTvShow = tvResponse[position]
        doAnswer { onMock ->
            (onMock.arguments[1] as RemoteResource.CallbackDetailTvShow)
                .onDetailTvReceived(dummyDetailTvShow)
        }.`when`(remote).getDetailTv(eq(id), any())
        val detailTvShow = LiveDataTestUtil.getValue(fakeRepo.getDetailTv(id))
        verify(remote).getDetailTv(eq(id), any())
        assertNotNull(detailTvShow)
        assertEquals(dummyDetailTvShow.name, detailTvShow.name)
    }

}