package com.bajp.submissionthree.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bajp.submissionthree.data.source.IRepository
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.data.source.remote.RemoteResource
import com.bajp.submissionthree.data.source.remote.response.CatalogResponse

class FakeRepository(private val remoteDataSource: RemoteResource) : IRepository {
    override fun getDataMovie(): LiveData<ContentEntity> {
        val response = MutableLiveData<ContentEntity>()
        remoteDataSource.getMovies(object : RemoteResource.CallbackListContent {
            override fun onAllMovieReceived(data: List<CatalogResponse>) {
                val results = ArrayList<CatalogEntity>()
                data.forEach {
                    it.imageSlider = IMAGE_URL + it.imageSlider
                    it.imagePoster = IMAGE_URL + it.imagePoster
                    results.add(
                        CatalogEntity(
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
                val results = ArrayList<CatalogEntity>()
                data.forEach {
                    it.imagePoster = IMAGE_URL + it.imagePoster
                    it.imageSlider = IMAGE_URL + it.imageSlider
                    results.add(
                        CatalogEntity(
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

    override fun getDetailMovie(id: Int): LiveData<CatalogEntity> {
        val response = MutableLiveData<CatalogEntity>()
        remoteDataSource.getDetailMovie(id, object : RemoteResource.CallbackDetailContent {
            override fun onDetailMovieReceived(data: CatalogResponse) {
                data.imagePoster = IMAGE_URL + data.imagePoster
                data.imageSlider = IMAGE_URL + data.imageSlider
                val result = CatalogEntity(
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

    override fun getDetailTv(id: Int): LiveData<CatalogEntity> {
        val response = MutableLiveData<CatalogEntity>()
        remoteDataSource.getDetailTv(id, object : RemoteResource.CallbackDetailContent {
            override fun onDetailTvReceived(data: TvShowResponse) {
                data.imagePoster = IMAGE_URL + data.imagePoster
                data.imageSlider = IMAGE_URL + data.imageSlider
                val result = CatalogEntity(
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