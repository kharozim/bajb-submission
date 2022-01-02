package com.bajp.submissiontwo.data.repository

import androidx.lifecycle.LiveData
import com.bajp.submissiontwo.data.entities.ContentEntity
import com.bajp.submissiontwo.data.entities.ContentItemEntity

interface IRepository {
    fun getDataMovie(): LiveData<ContentEntity>
    fun getDataTv(): LiveData<ContentEntity>
    fun getDetailMovie(id: Int): LiveData<ContentItemEntity>
    fun getDetailTv(id: Int): LiveData<ContentItemEntity>
}