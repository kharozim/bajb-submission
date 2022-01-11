package com.bajp.submissiontwo.data.source

import androidx.lifecycle.LiveData
import com.bajp.submissiontwo.data.source.local.entities.ContentEntity
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity

interface IRepository {
    fun getDataMovie(): LiveData<ContentEntity>
    fun getDataTv(): LiveData<ContentEntity>
    fun getDetailMovie(id: Int): LiveData<ContentItemEntity>
    fun getDetailTv(id: Int): LiveData<ContentItemEntity>
}