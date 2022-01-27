package com.bajp.submissionthree.data.source

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.data.source.local.LocalDataSource
import com.bajp.submissionthree.data.source.remote.vo.ApiResponse
import com.bajp.submissionthree.data.source.remote.RemoteResource
import com.bajp.submissionthree.data.source.remote.response.CatalogResponse
import com.bajp.submissionthree.vo.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import javax.inject.Inject

class Repository @Inject constructor(
    private val remoteDataSource: RemoteResource,
    private val localDataSource: LocalDataSource,
) : IRepository {

    private val dispatcher = Executors.newCachedThreadPool().asCoroutineDispatcher()
    private val scope = CoroutineScope(dispatcher)

    override fun getDataMovie(): LiveData<Resource<PagedList<CatalogEntity>>> {
        return object :
            NetworkBoundResource<PagedList<CatalogEntity>, List<CatalogResponse>>() {
            override fun loadFromDB(): LiveData<PagedList<CatalogEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(20)
                    setPageSize(4)
                    build()
                }.build()

                return LivePagedListBuilder(localDataSource.getMovies(), config).build()
            }

            override fun shouldFetch(data: PagedList<CatalogEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<CatalogResponse>>> {
                return remoteDataSource.getMovies()
            }

            override fun saveCallResult(data: List<CatalogResponse>) {
                val listMovie = ArrayList<CatalogEntity>()
                data.forEach {
                    listMovie.add(
                        CatalogEntity(
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

    override fun getDataTv(): LiveData<Resource<PagedList<CatalogEntity>>> {
        return object :
            NetworkBoundResource<PagedList<CatalogEntity>, List<CatalogResponse>>() {
            override fun loadFromDB(): LiveData<PagedList<CatalogEntity>> {
                val config = PagedList.Config.Builder().apply {
                    setEnablePlaceholders(false)
                    setInitialLoadSizeHint(20)
                    setPageSize(4)
                    build()
                }.build()
                return LivePagedListBuilder(localDataSource.getTvs(), config).build()
            }

            override fun shouldFetch(data: PagedList<CatalogEntity>?): Boolean {
                return data.isNullOrEmpty()
            }

            override fun createCall(): LiveData<ApiResponse<List<CatalogResponse>>> {
                return remoteDataSource.getDataTv()
            }

            override fun saveCallResult(data: List<CatalogResponse>) {
                val listTv = ArrayList<CatalogEntity>()
                data.forEach {
                    listTv.add(
                        CatalogEntity(
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

    override fun getDetailMovie(id: Int): LiveData<CatalogEntity> {
        return localDataSource.getDetailMovie(id)
    }

    override fun getDetailTv(id: Int): LiveData<CatalogEntity> {
        return localDataSource.getDetailTv(id)
    }

    override fun setFavorite(item: CatalogEntity) {
        scope.launch { localDataSource.setFav(item) }
    }

    override fun getFavorite(): LiveData<List<CatalogEntity>> =
        localDataSource.getFavorite()

}