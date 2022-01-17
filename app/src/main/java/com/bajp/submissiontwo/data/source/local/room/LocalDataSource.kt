package com.bajp.submissiontwo.data.source.local.room

import androidx.lifecycle.LiveData
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity

class LocalDataSource private constructor(private val dao: FavDao) {

    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(favDao: FavDao): LocalDataSource =
            INSTANCE ?: LocalDataSource(dao = favDao)
    }

    fun getFavMovie(): LiveData<List<ContentItemEntity>> = dao.getMovieFav(true)
    fun getFavTv(): LiveData<List<ContentItemEntity>> = dao.getMovieFav(false)
    fun

}