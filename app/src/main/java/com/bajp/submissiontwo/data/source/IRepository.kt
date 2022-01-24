package com.bajp.submissiontwo.data.source

import androidx.lifecycle.LiveData
import androidx.paging.PagedList
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.vo.Resource

interface IRepository {
    fun getDataMovie(): LiveData<Resource<PagedList<ContentItemEntity>>>
    fun getDataTv(): LiveData<Resource<PagedList<ContentItemEntity>>>
    fun getDetailMovie(id: Int): LiveData<ContentItemEntity>
    fun getDetailTv(id: Int): LiveData<ContentItemEntity>
    fun setFavorite(item: ContentItemEntity)
}