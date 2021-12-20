package com.bajp.submissionone.data.repository

import com.bajp.submissionone.data.ContentEntity
import com.bajp.submissionone.data.entities.ContentItemEntity

interface IRepository {
    fun getDataMovie(): ContentEntity
    fun getDataTv(): ContentEntity
    fun getDetailMovie(id : Int) : ContentItemEntity?
    fun getDetailTv(id : Int) : ContentItemEntity?
}