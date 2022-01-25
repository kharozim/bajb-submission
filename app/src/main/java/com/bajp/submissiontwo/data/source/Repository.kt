package com.bajp.submissiontwo.data.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.data.source.local.LocalDataSource
import com.bajp.submissiontwo.data.source.remote.vo.ApiResponse
import com.bajp.submissiontwo.data.source.remote.RemoteResource
import com.bajp.submissiontwo.data.source.remote.response.MovieResponse
import com.bajp.submissiontwo.utils.AppExecutors
import com.bajp.submissiontwo.vo.Resource

class Repository private constructor(
    private val remoteDataSource: RemoteResource,
    private val localDataSource: LocalDataSource,
    private val appExecutor: AppExecutors
) : IRepository {

    companion object {
        @Volatile
        private var instance: Repository? = null

        fun getInstance(
            remoteData: RemoteResource,
            localData: LocalDataSource,
            appExecutors: AppExecutors
        ): Repository =
            instance ?: synchronized(this) {
                Repository(remoteData, localData, appExecutors).apply { instance = this }
            }
    }

    override fun getDataMovie(): LiveData<Resource<PagedList<ContentItemEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ContentItemEntity>, List<MovieResponse>>(appExecutor) {
            override fun loadFromDB(): LiveData<PagedList<ContentItemEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                    build()
                }.build()

                return LivePagedListBuilder(localDataSource.getMovies(),config).build()
            }

            override fun shouldFetch(data: PagedList<ContentItemEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getMovies()
            }

            override fun saveCallResult(data: List<MovieResponse>) {
                val listMovie = ArrayList<ContentItemEntity>()
                data.forEach {
                    listMovie.add(
                        ContentItemEntity(
                            it.id ?: 0,
                            it.name ?: "",
                            it.description ?: "",
                            it.imagePoster ?: "",
                            it.imageSlider ?: "",
                            it.rating ?: 0.0,
                            it.ratingCount ?: 0,
                            it.releaseDate ?: "",
                            true,
                            false
                        )
                    )
                }
                localDataSource.insertMovies(listMovie)
            }

        }.asLiveData()
    }

    override fun getDataTv(): LiveData<Resource<PagedList<ContentItemEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ContentItemEntity>, List<MovieResponse>>(appExecutor) {
            override fun loadFromDB(): LiveData<PagedList<ContentItemEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(4)
                    setPageSize(4)
                    build()
                }.build()
                return LivePagedListBuilder(localDataSource.getTvs(),config).build()
            }

            override fun shouldFetch(data: PagedList<ContentItemEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<MovieResponse>>> {
                return remoteDataSource.getDataTv()
            }

            override fun saveCallResult(data: List<MovieResponse>) {
                val listTv = ArrayList<ContentItemEntity>()
                data.forEach {
                    listTv.add(
                        ContentItemEntity(
                            it.id ?: 0,
                            it.name ?: "",
                            it.description ?: "",
                            it.imagePoster ?: "",
                            it.imageSlider ?: "",
                            it.rating ?: 0.0,
                            it.ratingCount ?: 0,
                            it.releaseDate ?: "",
                            true,
                            false
                        )
                    )
                }
                localDataSource.insertTvs(listTv)
            }

        }.asLiveData()
    }

    override fun getDetailMovie(id: Int): LiveData<ContentItemEntity> {
       return localDataSource.getDetailMovie(id)
    }

    override fun getDetailTv(id: Int): LiveData<ContentItemEntity> {
        return localDataSource.getDetailTv(id)
    }

    override fun setFavorite(item: ContentItemEntity) {
       appExecutor.diskIO().execute { localDataSource.setFav(item) }
    }

    override fun getFavorite(): LiveData<List<ContentItemEntity>> =
        localDataSource.getFavorite()

}