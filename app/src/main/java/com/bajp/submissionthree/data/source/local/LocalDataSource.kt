package com.bajp.submissionthree.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.data.source.local.room.LocalDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: LocalDao) {

    fun getMovies(): DataSource.Factory<Int, CatalogEntity> = dao.getMovies()
    fun getTvs(): DataSource.Factory<Int, CatalogEntity> = dao.getTvs()
    fun getDetailMovie(id: Int): LiveData<CatalogEntity> = dao.getDetailMovie(id)
    fun getDetailTv(id: Int): LiveData<CatalogEntity> = dao.getDetailTv(id)

    fun insertMovies(items: List<CatalogEntity>) {
        items.asSequence().map { it.isMovie = true }.toList()
        dao.insertCatalog(items)
    }

    fun insertTvs(items: List<CatalogEntity>) {
        items.asSequence().map { it.isMovie = false }.toList()
        dao.insertCatalog(items)
    }

    fun setFav(item: CatalogEntity) {
        item.isFavorite = !item.isFavorite
        dao.setFav(item)
    }

    fun getFavorite() : LiveData<List<CatalogEntity>> = dao.getFavorite()
}