package com.bajp.submissiontwo.data.repository

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bajp.submissiontwo.data.repository.remote.RemoteResource
import com.bajp.submissiontwo.utils.DataUtil
import com.bajp.submissiontwo.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock

class RepositoryTest : TestCase() {

    @get:Rule
    var InstantTaskExecutorRule = InstantTaskExecutorRule()

    private val remote = mock(RemoteResource::class.java)
    private val fakeRepo = FakeRepository(remote)
    private val movieResponse = DataUtil.generateDataMovie()
    private val movieId = movieResponse[0].id
    private val tvResponse = DataUtil.generateDataMovie()
    private val tvId = tvResponse[0].id


    @Test
    fun getListMovie() {
        Log.e("TAG", "getListMovie: here", )
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

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}