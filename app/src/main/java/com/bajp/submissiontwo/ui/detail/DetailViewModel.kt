package com.bajp.submissiontwo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissiontwo.data.entities.ContentItemEntity
import com.bajp.submissiontwo.data.repository.IRepository

class DetailViewModel(private val repository: IRepository) : ViewModel() {

    fun getDetailMovie(id: Int): LiveData<ContentItemEntity> {
        val data = repository.getDetailMovie(id)
        return data
    }

    fun getDetailTv(id: Int): LiveData<ContentItemEntity> {
        val data = repository.getDetailTv(id)
        return data
    }
}