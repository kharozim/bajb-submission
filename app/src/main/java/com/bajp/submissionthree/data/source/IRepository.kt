package com.bajp.submissionthree.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.bajp.submissionthree.data.source.local.entities.CatalogEntity
import com.bajp.submissionthree.vo.Resource

interface IRepository {
    fun getDataMovie(): LiveData<Resource<PagedList<CatalogEntity>>>
    fun getDataTv(): LiveData<Resource<PagedList<CatalogEntity>>>
    fun getDetailMovie(id: Int): LiveData<CatalogEntity>
    fun getDetailTv(id: Int): LiveData<CatalogEntity>
    fun setFavorite(item: CatalogEntity)
    fun getFavorite() : LiveData<List<CatalogEntity>>
}