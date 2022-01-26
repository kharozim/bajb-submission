package com.bajp.submissionthree.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bajp.submissionthree.data.source.local.entities.ContentItemEntity
import com.bajp.submissionthree.data.source.IRepository

class DetailViewModel(private val repository: IRepository) : ViewModel() {

    fun getDetailMovie(id: Int): LiveData<ContentItemEntity> {
        return repository.getDetailMovie(id)
    }

    fun getDetailTv(id: Int): LiveData<ContentItemEntity> {
        return repository.getDetailTv(id)
    }

    fun setFav(item: ContentItemEntity) {
        repository.setFavorite(item)
    }
}