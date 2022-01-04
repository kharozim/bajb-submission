package com.bajp.submissiontwo.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bajp.submissiontwo.data.entities.ContentEntity
import com.bajp.submissiontwo.data.entities.ContentItemEntity
import com.bajp.submissiontwo.data.repository.remote.RemoteResource
import com.bajp.submissiontwo.data.repository.remote.response.MovieResponse
import com.bajp.submissiontwo.data.repository.remote.response.TvShowResponse

class Repository private constructor(private val remoteDataSource: RemoteResource) : IRepository {

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(remoteData: RemoteResource): Repository =
            instance ?: synchronized(this) {
                Repository(remoteData).apply { instance = this }
            }

        private const val IMAGE_URL = "https://image.tmdb.org/t/p/w500"
    }

    override fun getDataMovie(): LiveData<ContentEntity> {
        val response = MutableLiveData<ContentEntity>()
        remoteDataSource.getDataMovie(object : RemoteResource.CallbackListMovie {
            override fun onAllMovieReceived(data: List<MovieResponse>) {
                val result = ArrayList<ContentItemEntity>()
                data.forEach {
                    it.imagePoster = IMAGE_URL + it.imagePoster
                    it.imageSlider = IMAGE_URL + it.imageSlider
                    result.add(
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
                response.value = ContentEntity(result)
            }
        })
        return response
    }

    override fun getDataTv(): LiveData<ContentEntity> {
        val response = MutableLiveData<ContentEntity>()
        remoteDataSource.getDataTv(object : RemoteResource.CallBackListTvShow {
            override fun onAllTvShowReceived(data: List<TvShowResponse>) {
                val result = ArrayList<ContentItemEntity>()
                data.forEach {
                    it.imagePoster = IMAGE_URL + it.imagePoster
                    it.imageSlider = IMAGE_URL + it.imageSlider
                    result.add(
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
                response.postValue(ContentEntity(result))
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

}