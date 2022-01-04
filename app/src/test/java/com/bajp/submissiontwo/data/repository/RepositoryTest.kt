package com.bajp.submissiontwo.data.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.bajp.submissiontwo.data.repository.remote.RemoteResource
import com.bajp.submissiontwo.utils.DataUtil
import com.bajp.submissiontwo.utils.LiveDataTestUtil
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doAnswer
import com.nhaarman.mockitokotlin2.verify
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepositoryTest  {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()


    private val movieResponse = DataUtil.generateDataMovie()
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
        assertEquals(25, contentEntity.results[2].id)
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}