package com.bajp.submissiontwo.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissiontwo.data.source.local.entities.ContentItemEntity
import com.bajp.submissiontwo.data.source.IRepository

class DetailViewModel(private val repository: IRepository) : ViewModel() {

    fun getDetailMovie(id: Int): LiveData<ContentItemEntity> {
        return repository.getDetailMovie(id)
    }

    fun getDetailTv(id: Int): LiveData<ContentItemEntity> {
        return repository.getDetailTv(id)
    }
}