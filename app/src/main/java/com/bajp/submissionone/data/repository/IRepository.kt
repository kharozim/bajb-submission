package com.bajp.submissionone.data.repository

import com.bajp.submissionone.data.ContentEntity

interface IRepository {
    fun getDataMovie(dataMovie: String): ContentEntity
    fun getDataTv(dataMovie: String): ContentEntity
}