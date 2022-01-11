package com.bajp.submissiontwo.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bajp.submissiontwo.data.source.IRepository
import com.bajp.submissiontwo.data.source.local.entities.ContentEntity
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.data.source.remote.RemoteResource
import com.bajp.submissiontwo.data.source.remote.response.MovieResponse
import com.bajp.submissiontwo.data.source.remote.response.TvShowResponse

class FakeRepository(private val remoteDataSource: RemoteResource) : IRepository {
    override fun getDataMovie(): LiveData<ContentEntity> {
        val response = MutableLiveData<ContentEntity>()
        remoteDataSource.getDataMovie(object : RemoteResource.CallbackListMovie {
            override fun onAllMovieReceived(data: List<MovieResponse>) {
                val results = ArrayList<ContentItemEntity>()
                data.forEach {
                    it.imageSlider = IMAGE_URL + it.imageSlider
                    it.imagePoster = IMAGE_URL + it.imagePoster
                    results.add(
                        ContentItemEntity(
                            it.id ?: 0,
                            it.name ?: "",
                            it.description ?: "",
                            it.imagePoster ?: "",
                            it.imageSlider ?: "",
                            it.rating ?: 0.0,
                            it.ratingCount ?: 0,
                            it.releaseDate ?: ""
                        )
                    )
                }
                response.postValue(ContentEntity(results))
            }
        })
        return response
    }

    override fun getDataTv(): LiveData<ContentEntity> {
        val response = MutableLiveData<ContentEntity>()
        remoteDataSource.getDataTv(object : RemoteResource.CallBackListTvShow {
            override fun onAllTvShowReceived(data: List<TvShowResponse>) {
                val results = ArrayList<ContentItemEntity>()
                data.forEach {
                    it.imagePoster = IMAGE_URL + it.imagePoster
                    it.imageSlider = IMAGE_URL + it.imageSlider
                    results.add(
                        ContentItemEntity(
                            it.id ?: 0,
                            it.name ?: "",
                            it.description ?: "",
                            it.imagePoster ?: "",
                            it.imageSlider ?: "",
                            it.rating ?: 0.0,
                            it.ratingCount ?: 0,
                            it.releaseDate ?: ""
                        )
                    )
                }
                response.postValue(ContentEntity(results))
            }
        })
        return response
    }

    override fun getDetailMovie(id: Int): LiveData<ContentItemEntity> {
        val response = MutableLiveData<ContentItemEntity>()
        remoteDataSource.getDetailMovie(id, object : RemoteResource.CallbackDetailMovie {
            override fun onDetailMovieReceived(data: MovieResponse) {
                data.imagePoster = IMAGE_URL + data.imagePoster
                data.imageSlider = IMAGE_URL + data.imageSlider
                val result = ContentItemEntity(
                    data.id ?: 0,
                    data.name ?: "",
                    data.description ?: "",
                    data.imagePoster ?: "",
                    data.imageSlider ?: "",
                    data.rating ?: 0.0,
                    data.ratingCount ?: 0,
                    data.releaseDate ?: ""
                )
                response.postValue(result)
            }
        })
        return response
    }

    override fun getDetailTv(id: Int): LiveData<ContentItemEntity> {
        val response = MutableLiveData<ContentItemEntity>()
        remoteDataSource.getDetailTv(id, object : RemoteResource.CallbackDetailTvShow {
            override fun onDetailTvReceived(data: TvShowResponse) {
                data.imagePoster = IMAGE_URL + data.imagePoster
                data.imageSlider = IMAGE_URL + data.imageSlider
                val result = ContentItemEntity(
                    data.id ?: 0,
                    data.name ?: "",
                    data.description ?: "",
                    data.imagePoster ?: "",
                    data.imageSlider ?: "",
                    data.rating ?: 0.0,
                    data.ratingCount ?: 0,
                    data.releaseDate ?: ""
                )
                response.postValue(result)
            }
        })
        return response
    }

    companion object {
        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }
}