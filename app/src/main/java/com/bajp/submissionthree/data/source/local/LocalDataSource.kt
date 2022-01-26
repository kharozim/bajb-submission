package com.bajp.submissionthree.data.source.local

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.bajp.submissionthree.data.source.local.entities.ContentItemEntity
import com.bajp.submissionthree.data.source.local.room.LocalDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val dao: LocalDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(favDao: LocalDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(dao = favDao)
    }

    fun getMovies(): DataSource.Factory<Int, ContentItemEntity> = dao.getMovies()
    fun getTvs(): DataSource.Factory<Int, ContentItemEntity> = dao.getTvs()
    fun getDetailMovie(id: Int): LiveData<ContentItemEntity> = dao.getDetailMovie(id)
    fun getDetailTv(id: Int): LiveData<ContentItemEntity> = dao.getDetailTv(id)

    fun insertMovies(items: List<ContentItemEntity>) {
        items.asSequence().map { it.isMovie = true }.toList()
        dao.insertMovies(items)
    }

    fun insertTvs(items: List<ContentItemEntity>) {
        items.asSequence().map { it.isMovie = false }.toList()
        dao.insertMovies(items)
    }

    fun setFav(item: ContentItemEntity) {
        item.isFavorite = !item.isFavorite
        dao.setFav(item)
    }

    fun getFavorite() : LiveData<List<ContentItemEntity>> = dao.getFavorite()
}